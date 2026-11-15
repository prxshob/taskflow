package com.prashobh.taskflow.mapper;

import com.prashobh.taskflow.dto.TaskCreateRequest;
import com.prashobh.taskflow.dto.TaskResponse;
import com.prashobh.taskflow.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskMapperTest {

    private TaskMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new TaskMapper();
    }

    @Test
    void successfulCompleteTaskResponseCreation(){
        //arrange
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn JUnit");
        task.setCompleted(true);

        //Act
        final TaskResponse taskResponse = mapper.toTaskResponse(task);

        //Assert
        assertNotNull(taskResponse);
        assertEquals(1L,taskResponse.getId());
        assertEquals("Learn JUnit", taskResponse.getTitle());
        assertTrue(taskResponse.isCompleted());
    }
    @Test
    void successfulIncompleteTaskResponseCreation(){
        //arrange
        Task task = new Task();
        task.setId(2L);
        task.setTitle("Learn Mockito");
        task.setCompleted(false);

        //Act
        final TaskResponse taskResponse = mapper.toTaskResponse(task);

        //Assert
        assertNotNull(taskResponse);
        assertEquals(2L,taskResponse.getId());
        assertEquals("Learn Mockito", taskResponse.getTitle());
        assertFalse(taskResponse.isCompleted());
    }

    @Test
    void mapsCreateRequestToTask(){
        //arrange
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Practice Junit");
        request.setCompleted(true);

        //act
        final Task task = mapper.toTask(request);

        //assert
        assertNotNull(task);
        assertEquals("Practice Junit",task.getTitle());
        assertTrue(task.isCompleted());

    }
}
