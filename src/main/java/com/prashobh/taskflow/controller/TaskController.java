package com.prashobh.taskflow.controller;

import com.prashobh.taskflow.dto.TaskCreateRequest;
import com.prashobh.taskflow.dto.TaskResponse;
import com.prashobh.taskflow.dto.TaskUpdateRequest;
import com.prashobh.taskflow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks() {
        return ResponseEntity.ok(service.getTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id){
        return ResponseEntity.ok(service.getTask(id));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskCreateRequest request){
        TaskResponse createdTask = service.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest request
    ){
        TaskResponse updatedTask=service.updateTask(id,request);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask( @PathVariable Long id){
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
