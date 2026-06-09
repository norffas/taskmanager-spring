package com.islam.taskmanager_spring.repository;


import com.islam.taskmanager_spring.model.Task;
import com.islam.taskmanager_spring.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository {

    Task saveTask(Task task);

    Task findTaskById(int id);

    Task deleteTaskById(int id);

    Task update(int id, TaskStatus status);

    int statusAutoUpdate(LocalDateTime date);

    List<Task> findTasksByStatus(TaskStatus status);

    List<Task> findAll();


}
