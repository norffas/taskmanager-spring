package com.islam.taskmanager_spring.service;


import com.islam.taskmanager_spring.model.Task;

public class TaskServiceOperationResult {
    private final Task task;
    private final OperationStatus status;

    public TaskServiceOperationResult(OperationStatus status, Task task) {
        if(status == null)
            throw new NullPointerException();
        this.status = status;
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public OperationStatus getStatus() {
        return status;
    }
}
