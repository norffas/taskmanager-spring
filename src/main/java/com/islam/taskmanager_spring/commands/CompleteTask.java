package com.islam.taskmanager_spring.commands;


import com.islam.taskmanager_spring.model.Task;
import com.islam.taskmanager_spring.service.OperationStatus;
import com.islam.taskmanager_spring.service.TaskService;
import com.islam.taskmanager_spring.service.TaskServiceOperationResult;

public class CompleteTask implements Command {
    private final int id;
    private final TaskService manager;

    public CompleteTask(TaskService manager, int id) {
        this.id = id;
        this.manager = manager;
    }

    @Override
    public CommandResult execute(){
        TaskServiceOperationResult tmResult = manager.completeTask(id);
        Task task = tmResult.getTask();
        OperationStatus status = tmResult.getStatus();
        String msg;
        if (status == OperationStatus.NOT_FOUND) {
            msg = "Задача не найдена";
        }
        else if (status == OperationStatus.ALREADY_COMPLETED)
            msg = "Задача уже выполнена";
        else
            msg = "Задаче успешно присвоен статус выполненной.";
        return new CommandResult(msg, task);
    }
}
