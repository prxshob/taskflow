package com.prashobh.taskflow.service;

import com.prashobh.taskflow.dto.TaskCreateRequest;
import com.prashobh.taskflow.dto.TaskResponse;
import com.prashobh.taskflow.dto.TaskUpdateRequest;
import com.prashobh.taskflow.exception.DuplicateTaskException;
import com.prashobh.taskflow.exception.TaskNotFoundException;
import com.prashobh.taskflow.mapper.TaskMapper;
import com.prashobh.taskflow.model.Task;
import com.prashobh.taskflow.repository.TaskRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository repository;
    private final TaskMapper mapper;

    public TaskService(TaskRepository repository, TaskMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<TaskResponse>  getTasks(){
        return mapper.toTaskResponseList(repository.findAll());
    }


//    public TaskResponse getTask(Long id){
//        Optional<Task> task = repository.findById(id);
//        if (task.isEmpty()) {
//            throw new TaskNotFoundException(id);
//        }
//        return mapper.toTaskResponse(task.get());
//    }

    public TaskResponse getTask(Long id){

        return mapper.toTaskResponse(repository.findById(id).orElseThrow(
                () ->  new TaskNotFoundException(id)
        ));
    }

    @Transactional
    public TaskResponse createTask(TaskCreateRequest request){
        Task task = mapper.toTask(request);
        if(repository.findByTitle(task.getTitle()).isPresent()){
            throw new DuplicateTaskException("Task with title '"+task.getTitle()+"' already exists!");
        }
        return mapper.toTaskResponse(repository.save(task));
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskUpdateRequest request){

        Task existingTask = repository.findById(id).orElseThrow(
                () ->  new TaskNotFoundException(id,"Unable to update!")
        );

        Task Task = mapper.toTask(request);

        var taskWithTitle = repository.findByTitle(Task.getTitle());

        if (taskWithTitle.isPresent() && !taskWithTitle.get().getId().equals(id)) {
            throw new DuplicateTaskException("Task with title '"+Task.getTitle()+"' already exists!");
        }

        existingTask.setTitle(Task.getTitle());
        existingTask.setCompleted(Task.isCompleted());

        Task updatedTask = repository.save(existingTask);
        return mapper.toTaskResponse(updatedTask);

//        if(repository.findById(id).isEmpty()){
//            throw new TaskNotFoundException(id,"Unable to update!");
//        }
//        Task task = mapper.toTask(request);
//        Optional<Task> taskWithTitle = repository.findByTitle(task.getTitle());
//        if(taskWithTitle.isPresent()  && !taskWithTitle.get().getId().equals(id)){
//            throw new DuplicateTaskException("Task with title '"+task.getTitle()+"' already exists!");
//        }
//        Task updatedTask = repository.update(id,task).orElseThrow(() -> new TaskNotFoundException(id, "Unable to update!"));
//        return mapper.toTaskResponse(updatedTask);
    }


    @Transactional
    public void deleteTask(Long id){
        if(!repository.existsById(id)){
            throw new TaskNotFoundException(id,"Unable to delete!");
        }
        repository.deleteById(id);
    }
}
