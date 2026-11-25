package com.prashobh.taskflow.repository;

import com.prashobh.taskflow.model.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TaskRepositoryTest {

    @Autowired
    private TaskRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void successfullySavesAndRetrievesTask() {
        // Arrange
        Task task = new Task();
        task.setTitle("Learn Spring Boot JPA");
        task.setCompleted(false);

        // Act: save the task into PostgreSQL
        Task savedTask = repository.saveAndFlush(task);

        // Clear the persistence context so the next query
        // retrieves the entity from the database.
        entityManager.clear();

        Optional<Task> retrievedTask =
                repository.findByTitle("Learn Spring Boot JPA");

        // Assert
        assertTrue(retrievedTask.isPresent());

        Task actualTask = retrievedTask.get();

        assertNotNull(savedTask.getId());
        assertEquals(savedTask.getId(), actualTask.getId());
        assertEquals("Learn Spring Boot JPA", actualTask.getTitle());
        assertFalse(actualTask.isCompleted());
        assertNotNull(actualTask.getCreatedAt());
    }
}