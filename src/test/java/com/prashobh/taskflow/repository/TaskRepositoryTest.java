package com.prashobh.taskflow.repository;

import com.prashobh.taskflow.model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskRepositoryTest {

    @Test
    void getTasksImmutabilityTest() {
        //arrange
        TaskRepository repository = new TaskRepository();
        repository.save(new Task(99L, "Learn SpringBoot", true));
        repository.save(new Task(98L, "Learn JUnit", true));
        repository.save(new Task(97L, "Learn Mockito", true));

        //act
        List<Task> responseTasks = repository.findAll();

        //assert
        assertEquals(3,responseTasks.size());
        assertThrows(UnsupportedOperationException.class,()-> responseTasks.clear());
        assertEquals(3, repository.findAll().size());
    }
}
