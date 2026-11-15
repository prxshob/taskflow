package com.prashobh.taskflow.controller;

import com.prashobh.taskflow.dto.TaskCreateRequest;
import com.prashobh.taskflow.dto.TaskResponse;
import com.prashobh.taskflow.exception.TaskNotFoundException;
import com.prashobh.taskflow.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService service;

    @Test
    void getTasksReturnsOkWithEmptyList() throws Exception{
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getTasksReturnsOkWithNonEmptyList() throws Exception{
        TaskResponse response1 = new TaskResponse();
        response1.setId(1L);
        response1.setTitle("Learn JUnit");
        response1.setCompleted(true);
        response1.setCreatedAt(Instant.now());
        TaskResponse response2 = new TaskResponse();
        response2.setId(2L);
        response2.setTitle("Learn Mockito");
        response2.setCompleted(true);
        response2.setCreatedAt(Instant.now());
        List<TaskResponse> responses = List.of(response1,response2);
        when(service.getTasks()).thenReturn(responses);
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Learn JUnit"))
                .andExpect(jsonPath("$[0].completed").value(true))
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Learn Mockito"))
                .andExpect(jsonPath("$[1].completed").value(true))
                .andExpect(jsonPath("$[1].createdAt").exists());
        verify(service).getTasks();
    }

    @Test
    void getTaskReturnsOkWithTask() throws Exception{
        //arrange
        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setTitle("Learn JUnit");
        response.setCompleted(true);
        response.setCreatedAt(Instant.now());
        when(service.getTask(1L)).thenReturn(response);

        //act+assert
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Learn JUnit"))
                .andExpect(jsonPath("$.completed").value(true))
                .andExpect(jsonPath("$.createdAt").exists());
        verify(service).getTask(1L);
    }

    @Test
    void getTaskReturnsNotFoundWhenTaskDoesNotExist() throws Exception{
        //arrange
        when(service.getTask(1L)).thenThrow(new TaskNotFoundException(1L));

        //act+assert
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("TASK_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Task 1 doesn't exist"));
        verify(service).getTask(1L);
    }

    @Test
    void postTaskReturnsCreatedWithTask() throws Exception {
        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setTitle("Learn JUnit");
        response.setCompleted(true);
        response.setCreatedAt(Instant.now());
        when(service.createTask(any(TaskCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "title": "Learn JUnit",
                    "completed": true
                }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Learn JUnit"))
                .andExpect(jsonPath("$.completed").value(true))
                .andExpect(jsonPath("$.createdAt").exists());
        verify(service).createTask(any(TaskCreateRequest.class));
    }
}
