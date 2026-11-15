package com.prashobh.taskflow.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task " + id + " doesn't exist");
    }

    public TaskNotFoundException(Long id, String action) {
        super(action + " Task " + id + " doesn't exist");
    }
}
