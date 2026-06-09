package com.islam.taskmanager_spring.commands;


import com.islam.taskmanager_spring.service.OperationStatus;
import com.islam.taskmanager_spring.service.TaskService;
import com.islam.taskmanager_spring.service.TaskServiceOperationResult;

public class AddTask implements Command{
    private final TaskService manager;
    private final String description;

    public AddTask(TaskService manager, String description) {
        this.description = description;
        this.manager = manager;
    }

    @Override
    public CommandResult execute() {
        TaskServiceOperationResult result = manager.addTask(description);
        if(result.getStatus() == OperationStatus.ADDED){
            return new CommandResult("Задача успешно создана", result.getTask());
        }
        else if(result.getStatus() == OperationStatus.NOT_ADDED_INVALID_DESCRIPTION){
            return  new CommandResult("Описание задачи содержит запрещенные символы.");
        }
        else if(result.getStatus() == OperationStatus.NOT_ADDED_EMPTY_DESCRIPTION){
            return new CommandResult("Описание задачи не может быть пустым.");
        }
        else{
            return new CommandResult("Не удалось добавить задачу.");
        }
    }
}
