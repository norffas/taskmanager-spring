package com.islam.taskmanager_spring.repository;

import com.islam.taskmanager_spring.model.Task;
import com.islam.taskmanager_spring.model.TaskStatus;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {

    @Modifying
    @Query("UPDATE tasks SET task_status = 'ABANDONED' " +
            "WHERE task_status = 'PENDING' " +
            "AND created_at <= :date")
    public int statusAutoUpdate(
            @Param("date") LocalDateTime date);

    @Override
    @NonNull
    List<Task> findAll();

    public List<Task> findByStatus(TaskStatus status);

}
