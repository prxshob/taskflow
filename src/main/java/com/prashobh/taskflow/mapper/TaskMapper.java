package com.prashobh.taskflow.mapper;

import com.prashobh.taskflow.dto.TaskCreateRequest;
import com.prashobh.taskflow.dto.TaskResponse;
import com.prashobh.taskflow.dto.TaskUpdateRequest;
import com.prashobh.taskflow.model.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {
    public Task toTask(TaskCreateRequest request){
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setCompleted(request.getCompleted());
        return task;
    }

    public Task toTask(TaskUpdateRequest request){
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setCompleted(request.getCompleted());
        return task;
    }

    public TaskResponse toTaskResponse(Task task){
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setCompleted(task.isCompleted());
        response.setCreatedAt(task.getCreatedAt());
        return response;
    }

    public List<TaskResponse> toTaskResponseList(List<Task> tasks) {
//        List<TaskResponse> listOfTasks = new ArrayList<>();
//        for (Task task : tasks) {
//            listOfTasks.add(toTaskResponse(task));
//        }
//        return listOfTasks;

        return tasks.stream()
                .map(this::toTaskResponse)
                .toList();
    }
}
