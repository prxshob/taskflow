package com.prashobh.taskflow.exception;

public class DuplicateTaskException extends RuntimeException {
    public DuplicateTaskException(String message) {
        super(message);
    }
}
