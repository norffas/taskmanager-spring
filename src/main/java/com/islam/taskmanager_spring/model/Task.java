package com.islam.taskmanager_spring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Table("tasks")
public class Task {
    @Id
    private final Integer id;
    private final String description;
    @Column("task_status")
    private TaskStatus status;
    private final LocalDateTime createdAt;

    public TaskStatus getStatus() {
        return status;
    }

    private void setStatus(TaskStatus status){
        this.status = status;
    }

    public Task(String description) {
        this.id = null;
        this.description = description;
        this.status = TaskStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    @PersistenceCreator
    public Task(int id, String description, TaskStatus status, LocalDateTime createdAt){
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

    public boolean isCompleted() {
        return this.status == TaskStatus.COMPLETED;
    }

    public void complete() {
        if (this.status != TaskStatus.COMPLETED)
            this.status = TaskStatus.COMPLETED;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return String.format("Task[%d: %s, completed=%s, created=%s]",
                id, description, status, createdAt);
    }

    public String toDisplay(){
        String string = status.getDisplayName();
        return String.format("№%d. %s, статус задачи: %s, создана: %s.%s.%s", id, description, string, createdAt.getDayOfMonth(), createdAt.getMonthValue(), createdAt.getYear());
    }
}
