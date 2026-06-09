package com.islam.taskmanager_spring.commands;


import com.islam.taskmanager_spring.service.OperationStatus;
import com.islam.taskmanager_spring.service.TaskService;
import com.islam.taskmanager_spring.service.TaskServiceOperationResult;

public class DeleteTask implements Command {
    private final TaskService manager;
    private final int id;

    public DeleteTask(TaskService manager, int id) {
        this.manager = manager;
        this.id = id;
    }


    @Override
    public CommandResult execute() {
        TaskServiceOperationResult result = manager.deleteTask(id);
        if(result.getStatus() == OperationStatus.NOT_FOUND)
            return new CommandResult("Задача не найдена");
        else{
            return new CommandResult("Задача успешно удалена", result.getTask());
        }
    }
}
