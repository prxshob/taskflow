package com.prashobh.taskflow.repository;

import com.prashobh.taskflow.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {

    Optional<Task> findByTitle(String title);
}