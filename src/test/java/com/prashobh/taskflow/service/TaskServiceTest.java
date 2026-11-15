package com.prashobh.taskflow.service;

import com.prashobh.taskflow.dto.TaskCreateRequest;
import com.prashobh.taskflow.dto.TaskResponse;
import com.prashobh.taskflow.dto.TaskUpdateRequest;
import com.prashobh.taskflow.exception.DuplicateTaskException;
import com.prashobh.taskflow.exception.TaskNotFoundException;
import com.prashobh.taskflow.mapper.TaskMapper;
import com.prashobh.taskflow.model.Task;
import com.prashobh.taskflow.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository repository;
    @Mock
    private TaskMapper mapper;
    @InjectMocks
    private TaskService taskService;

    @Test
    void sucessfullyGetTasks(){
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(99L,"Learn SpringBoot",true);
        Task task2 = new Task(98L,"Learn JUnit",true);
        Task task3 = new Task(97L,"Learn Mockito",true);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        List<TaskResponse> responses = new ArrayList<>();
        TaskResponse response1 = new TaskResponse();
        response1.setId(99L);
        response1.setTitle("Learn SpringBoot");
        response1.setCompleted(true);
        TaskResponse response2 = new TaskResponse();
        response2.setId(98L);
        response2.setTitle("Learn JUnit");
        response2.setCompleted(true);
        TaskResponse response3 = new TaskResponse();
        response3.setId(97L);
        response3.setTitle("Learn Mockito");
        response3.setCompleted(true);

        responses.add(response1);
        responses.add(response2);
        responses.add(response3);


        when(repository.findAll()).thenReturn(List.copyOf(tasks));
        when(mapper.toTaskResponseList(tasks)).thenReturn(responses);

        List<TaskResponse> returnResponses = taskService.getTasks();

        assertNotNull(returnResponses);
        assertEquals(3, returnResponses.size());

        assertEquals(99L, returnResponses.get(0).getId());
        assertEquals("Learn SpringBoot", returnResponses.get(0).getTitle());
        assertTrue(returnResponses.get(0).isCompleted());
        assertEquals(98L, returnResponses.get(1).getId());
        assertEquals("Learn JUnit", returnResponses.get(1).getTitle());
        assertTrue(returnResponses.get(1).isCompleted());
        assertEquals(97L, returnResponses.get(2).getId());
        assertEquals("Learn Mockito", returnResponses.get(2).getTitle());
        assertTrue(returnResponses.get(2).isCompleted());

        verify(repository).findAll();
        verify(mapper).toTaskResponseList(tasks);

    }

    @Test
    void returnsEmptyListWhenNoTasksExist(){
        List<Task> tasks = new ArrayList<>();
        List<TaskResponse> responses = new ArrayList<>();
        when(repository.findAll()).thenReturn(tasks);
        when(mapper.toTaskResponseList(tasks)).thenReturn(responses);

        List<TaskResponse> returnResponses = taskService.getTasks();

        assertNotNull(returnResponses);
        assertEquals(0, returnResponses.size());
        assertTrue(returnResponses.isEmpty());
        verify(repository).findAll();
        verify(mapper).toTaskResponseList(tasks);
    }

    @Test
    void throwsExceptionWhenRetrievingTasksFails(){
        when(repository.findAll()).thenThrow(new RuntimeException("Database failure!"));

        RuntimeException exception = assertThrows(RuntimeException.class,()->taskService.getTasks());

        assertEquals("Database failure!",exception.getMessage());
        verify(repository).findAll();
        verify(mapper,never()).toTaskResponseList(anyList());
    }

    @Test
    void successfullyGetsTask(){
        //Arrange
        Task task = new Task();
        task.setId(99L);
        task.setTitle("Learn Mockito");
        task.setCompleted(true);
        TaskResponse response = new TaskResponse();
        response.setId(99L);
        response.setTitle("Learn Mockito");
        response.setCompleted(true);

        when(repository.findById(99L)).thenReturn(Optional.of(task));
        when(mapper.toTaskResponse(task)).thenReturn(response);
        //Act
        TaskResponse returnResponse = taskService.getTask(99L);

        //Assert
        assertNotNull(returnResponse);
        assertEquals(99L,returnResponse.getId());
        assertEquals("Learn Mockito", returnResponse.getTitle());
        assertTrue(returnResponse.isCompleted());
        verify(repository).findById(99L);
        verify(mapper).toTaskResponse(any(Task.class));
    }

    @Test
    void throwsTaskNotFoundException(){
        //Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());
        //Act+Assert
        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class,()->taskService.getTask(99L));
        assertEquals("Task 99 doesn't exist",exception.getMessage());
        verify(mapper, never()).toTaskResponse(any(Task.class));
    }

    @Test
    void successfullyCreatesTask(){
        //Arrange
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Learn Mockito");
        request.setCompleted(false);

        Task task = new Task();
        task.setTitle("Learn Mockito");
        task.setCompleted(false);

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("Learn Mockito");
        savedTask.setCompleted(false);

        TaskResponse returnResponse = new TaskResponse();
        returnResponse.setId(1L);
        returnResponse.setTitle("Learn Mockito");
        returnResponse.setCompleted(false);
        returnResponse.setCreatedAt(Instant.now());

        when(mapper.toTask(request)).thenReturn(task);
        when(repository.findByTitle(task.getTitle())).thenReturn(Optional.empty());
        when(repository.save(task)).thenReturn(savedTask);
        when(mapper.toTaskResponse(savedTask)).thenReturn(returnResponse);

        //Act
        TaskResponse response = taskService.createTask(request);

        //Assert
        assertNotNull(response);
        assertEquals("Learn Mockito",response.getTitle());
        assertFalse(response.isCompleted());
        assertEquals(1L,response.getId());
        verify(repository).save(task);
    }

    @Test
    void throwsExceptionWhenTitleAlreadyExists(){
        //Arrange
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Learn Mockito");
        existingTask.setCompleted(false);

        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Learn Mockito");
        request.setCompleted(false);

        Task task = new Task();
        task.setTitle("Learn Mockito");
        task.setCompleted(false);

        when(mapper.toTask(request)).thenReturn(task);
        when(repository.findByTitle(task.getTitle())).thenReturn(Optional.of(existingTask));

        //Act+Assert
        DuplicateTaskException exception = assertThrows(DuplicateTaskException.class, ()->taskService.createTask(request));
        assertEquals("Task with title 'Learn Mockito' already exists!",exception.getMessage());
        verify(repository,never()).save(any(Task.class));
    }

    @Test
    void throwsTaskNotFoundExceptionWhenUpdating(){
        //Arrange
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("Learn Mockito");
        request.setCompleted(false);
        when(repository.findById(99L)).thenReturn(Optional.empty());

        //Act+Assert
        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class,()->taskService.updateTask(99L,request));
        assertEquals("Unable to update! Task 99 doesn't exist",exception.getMessage());
        verify(repository).findById(99L);
        verify(mapper, never()).toTask(request);
        verify(repository,never()).update(eq(99L),any(Task.class));
        verify(mapper,never()).toTaskResponse(any(Task.class));
    }

    @Test
    void throwsFallbackTaskNotFoundExceptionWhenUpdating(){
        //arrange
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("Learn Mockito");
        request.setCompleted(false);
        Task existingTask = new Task();
        existingTask.setId(99L);
        existingTask.setTitle("Learn Junit");
        existingTask.setCompleted(false);
        Task updateTask = new Task();
        updateTask.setId(99L);
        updateTask.setTitle("Learn Mockito");
        updateTask.setCompleted(false);

        when(repository.findById(99L)).thenReturn(Optional.of(existingTask));
        when(mapper.toTask(request)).thenReturn(updateTask);
        when(repository.findByTitle(updateTask.getTitle())).thenReturn(Optional.empty());
        when(repository.update(99L,updateTask)).thenReturn(Optional.empty());

        //Act
        TaskNotFoundException exception =assertThrows(TaskNotFoundException.class,()->taskService.updateTask(99L,request));
        assertEquals("Unable to update! Task 99 doesn't exist",exception.getMessage());
        verify(repository).findById(99L);
        verify(mapper).toTask(request);
        verify(repository).findByTitle(updateTask.getTitle());
        verify(repository).update(99L,updateTask);
        verify(mapper,never()).toTaskResponse(any(Task.class));

    }

    @Test
    void throwsDuplicateTaskException(){
        //arrange
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("Learn Mockito");
        request.setCompleted(false);
        Task existingTask = new Task();
        existingTask.setId(99L);
        existingTask.setTitle("Learn Junit");
        existingTask.setCompleted(false);
        Task updateTask = new Task();
        updateTask.setId(99L);
        updateTask.setTitle("Learn Mockito");
        updateTask.setCompleted(false);
        Task existingTaskWithSameTitle = new Task();
        existingTaskWithSameTitle.setId(96L);
        existingTaskWithSameTitle.setTitle("Learn Mockito");
        existingTaskWithSameTitle.setCompleted(false);

        when(repository.findById(99L)).thenReturn(Optional.of(existingTask));
        when(mapper.toTask(request)).thenReturn(updateTask);
        when(repository.findByTitle(updateTask.getTitle())).thenReturn(Optional.of(existingTaskWithSameTitle));
        //act+assert
        DuplicateTaskException exception = assertThrows(DuplicateTaskException.class, ()->taskService.updateTask(99L,request));
        assertEquals("Task with title 'Learn Mockito' already exists!",exception.getMessage());
        verify(repository).findById(99L);
        verify(mapper).toTask(request);
        verify(repository).findByTitle(updateTask.getTitle());
        verify(repository,never()).update(eq(99L),any(Task.class));
        verify(mapper,never()).toTaskResponse(any(Task.class));
    }

    @Test
    void successfullyUpdatesTask(){
        //Arrange
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("Learn Mockito");
        request.setCompleted(false);
        Task existingTask = new Task();
        existingTask.setId(99L);
        existingTask.setTitle("Learn Junit");
        existingTask.setCompleted(false);
        Task updateTask = new Task();
        updateTask.setId(99L);
        updateTask.setTitle("Learn Mockito");
        updateTask.setCompleted(false);
        Task updatedTask = new Task();
        updatedTask.setId(99L);
        updatedTask.setTitle("Learn Mockito");
        updatedTask.setCompleted(false);
        TaskResponse response = new TaskResponse();
        response.setId(99L);
        response.setTitle("Learn Mockito");
        response.setCompleted(false);

        when(repository.findById(99L)).thenReturn(Optional.of(existingTask));
        when(mapper.toTask(request)).thenReturn(updateTask);
        when(repository.findByTitle(updateTask.getTitle())).thenReturn(Optional.empty());
        when(repository.update(99L,updateTask)).thenReturn(Optional.of(updatedTask));
        when(mapper.toTaskResponse(updatedTask)).thenReturn(response);

        //Act
        TaskResponse returnResponse = taskService.updateTask(99L,request);

        //Assert
        assertNotNull(returnResponse);
        assertEquals(99L,returnResponse.getId());
        assertEquals("Learn Mockito",returnResponse.getTitle());
        assertFalse(returnResponse.isCompleted());
        verify(repository).findById(99L);
        verify(mapper).toTask(request);
        verify(repository).findByTitle(updateTask.getTitle());
        verify(repository).update(eq(99L),any(Task.class));
        verify(mapper).toTaskResponse(any(Task.class));
    }

    @Test
    void successfullyUpdatesTaskWithSameTitle(){
        //Arrange
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("Learn Mockito");
        request.setCompleted(false);
        Task existingTask = new Task();
        existingTask.setId(99L);
        existingTask.setTitle("Learn Mockito");
        existingTask.setCompleted(true);
        Task updateTask = new Task();
        updateTask.setId(99L);
        updateTask.setTitle("Learn Mockito");
        updateTask.setCompleted(false);
        Task updatedTask = new Task();
        updatedTask.setId(99L);
        updatedTask.setTitle("Learn Mockito");
        updatedTask.setCompleted(false);
        TaskResponse response = new TaskResponse();
        response.setId(99L);
        response.setTitle("Learn Mockito");
        response.setCompleted(false);

        when(repository.findById(99L)).thenReturn(Optional.of(existingTask));
        when(mapper.toTask(request)).thenReturn(updateTask);
        when(repository.findByTitle(updateTask.getTitle())).thenReturn(Optional.of(existingTask));
        when(repository.update(99L,updateTask)).thenReturn(Optional.of(updatedTask));
        when(mapper.toTaskResponse(updatedTask)).thenReturn(response);

        //Act
        TaskResponse returnResponse = taskService.updateTask(99L,request);

        //Assert
        assertNotNull(returnResponse);
        assertEquals(99L,returnResponse.getId());
        assertEquals("Learn Mockito",returnResponse.getTitle());
        assertFalse(returnResponse.isCompleted());
        verify(repository).findById(99L);
        verify(mapper).toTask(request);
        verify(repository).findByTitle(updateTask.getTitle());
        verify(repository).update(eq(99L),any(Task.class));
        verify(mapper).toTaskResponse(any(Task.class));
    }

    @Test
    void successfullyDeletesTask(){
        when(repository.deleteById(99L)).thenReturn(true);
        taskService.deleteTask(99L);
        verify(repository).deleteById(99L);
    }

    @Test
    void throwsTaskNotFoundExceptionWhenDeleting(){
        when(repository.deleteById(99L)).thenReturn(false);
        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class,()->taskService.deleteTask(99L));
        assertEquals("Unable to delete! Task 99 doesn't exist", exception.getMessage());
        verify(repository).deleteById(99L);
    }
}