package com.islam.taskmanager_spring.ui;


import com.islam.taskmanager_spring.commands.CommandCreator;
import com.islam.taskmanager_spring.commands.CommandResult;
import com.islam.taskmanager_spring.commands.Parameters;
import com.islam.taskmanager_spring.model.Task;
import com.islam.taskmanager_spring.repository.RepositoryException;
import com.islam.taskmanager_spring.service.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class UserInterface implements CommandLineRunner {
    private final TaskService service;
    private volatile boolean exit = false;
    private final Output output;
    private final Input input;

    public UserInterface(TaskService manager, Output output, Input input) {
        this.service = manager;
        this.output = output;
        this.input = input;
    }

    @Override
    public void run(String[] args) {

        Runnable autoUpdate = () ->{
            while(!exit){
                try {
                    Thread.sleep(50000);
                    int quality = service.updateAbandonedStatus();
                    output.printMessage(quality + " изменили статус задачи.");
                } catch (InterruptedException e) {
                    return;
                }
            }
        };
        Thread updateStatusThread = new Thread(autoUpdate);
        updateStatusThread.start();
        CommandCreator commandCreator = new CommandCreator();
        while (true) {
            output.printMenu();
            Menu choice = Menu.getMenuObject(readIntSafely());
            if (choice == null) {
                output.printError("Ошибка ввода");
                continue;
            }
            List<Parameters> params = commandCreator.getParameters(choice);
            Map<Parameters, Object> parameters = new EnumMap<>(Parameters.class);
            for (Parameters param : params){
                switch (param){
                    case TASK_DESCRIPTION:
                        output.prompt("Введите описание задачи: ");
                        parameters.put(param, readNonEmptyLineSafely());
                        break;
                    case TASK_ID:
                        output.prompt("Введите номер задача: ");
                        parameters.put(param, readIntSafely());
                        break;
                    default:
                        output.printError("Введите корректную команду.");
                }
            }
            CommandResult result;
            try{
                result = commandCreator.createCommand(choice, service, parameters).execute();
            }
            catch (RepositoryException e){
                output.printError(e.getMessage());
                continue;
            }
            catch (IllegalArgumentException e){
                output.printError(e.getMessage());
                continue;
            }
            String message = result.getMessage();
            Task task = result.getTask();
            List<Task> tasks = result.getTasks();
            exit = result.isExit();
            output.printMessage(message);
            output.printTask(task);
            output.printTasks(tasks);
            if(exit){
                input.closeInput();
                updateStatusThread.interrupt();
                break;
            }
        }

    }

    int readIntSafely(){
        while(true){
            try {
                return input.readInt();
            }
            catch (InputException e){
                output.printError(e.getMessage());
            }
        }
    }

    String readNonEmptyLineSafely(){
        while(true){
            try{
                return input.readNonEmptyLine();
            }
            catch (InputException e){
                output.printError(e.getMessage());
            }
        }
    }

}
