package com.islam.taskmanager_spring.service;

import com.islam.taskmanager_spring.model.Task;
import com.islam.taskmanager_spring.model.TaskStatus;
import com.islam.taskmanager_spring.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class TaskService {
    private final TaskRepository repo;
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public synchronized TaskServiceOperationResult addTask(String description) {
        if(description == null || description.trim().isEmpty()){
            return new TaskServiceOperationResult(OperationStatus.NOT_ADDED_EMPTY_DESCRIPTION, null);
        }
        description = description.trim();
        if(!isValidDescription(description))
            return new TaskServiceOperationResult(OperationStatus.NOT_ADDED_INVALID_DESCRIPTION, null);
        Task task = new Task(description);
        task = repo.save(task);
        return new TaskServiceOperationResult(OperationStatus.ADDED, task);
    }

    public Optional<Task> findTaskById(int id) {
        return repo.findById(id);
    }

    public synchronized TaskServiceOperationResult deleteTask(int id){
        Task task;
        Optional<Task> optionalTask = findTaskById(id);
        if(optionalTask.isPresent()){
            task = optionalTask.get();
            repo.deleteById(id);
            return new TaskServiceOperationResult(OperationStatus.DELETED_NOW, task);
        }
        else{
            return new TaskServiceOperationResult(OperationStatus.NOT_FOUND, null);
        }
    }

    public synchronized TaskServiceOperationResult completeTask(int id){
        Optional<Task> optionalTask = repo.findById(id);
        Task task;
        if(optionalTask.isPresent()){
            task = optionalTask.get();
            if(task.isCompleted())
                return new TaskServiceOperationResult(OperationStatus.ALREADY_COMPLETED, task);
            else{
                task.complete();
                repo.save(task);
                return new TaskServiceOperationResult(OperationStatus.COMPLETED_NOW, task);
            }
        }
        else{
            return new TaskServiceOperationResult(OperationStatus.NOT_FOUND, null);
        }
    }

    public synchronized int updateAbandonedStatus() {
        return repo.statusAutoUpdate(LocalDateTime.now().minusDays(7));
    }

    public synchronized List<Task> getAllTasks() {
        return repo.findAll();
    }

    public synchronized List<Task> getCompletedTasks(){
        return repo.findByStatus(TaskStatus.COMPLETED);
    }

    public synchronized List<Task> getPendingTasks(){
        return repo.findByStatus(TaskStatus.PENDING);
    }

    public synchronized List<Task> getAbandonedTasks(){
        return repo.findByStatus(TaskStatus.ABANDONED);
    }

    private boolean isValidDescription(String description){
        return description.matches("[\\p{L}\\p{N} :,.?!\\-\\[\\]{}\\\\/]+") && description.matches(".*[\\p{L}\\p{N}].*");
    }

}
