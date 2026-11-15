package com.prashobh.taskflow.repository;

import com.prashobh.taskflow.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {
    private final List<Task> tasks = new ArrayList<>();

    private Long nextId=1L;

    public TaskRepository() {
    }

    public List<Task> findAll() {
        return List.copyOf(tasks);
    }

    public Optional<Task> findById(Long id){
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    public Optional<Task> findByTitle(String title){
        return tasks.stream()
                .filter(task -> task.getTitle().equals(title))
                .findFirst();
    }

    public Task save(Task task){
        task.setId(nextId);
        nextId++;
        tasks.add(task);
        return task;
    }

    public Optional<Task> update(Long id, Task task) {
        for(Task updatedTask :tasks){
            if(updatedTask.getId().equals(id)){
                updatedTask.setTitle(task.getTitle());
                updatedTask.setCompleted(task.isCompleted());
                return Optional.of(updatedTask);
            }
        }
        return Optional.empty();
    }

    public boolean deleteById(Long id) {
        return tasks.removeIf(task -> task.getId().equals(id));
    }
}