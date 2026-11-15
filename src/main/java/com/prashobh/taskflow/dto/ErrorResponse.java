package com.prashobh.taskflow.dto;

import java.time.Instant;
import java.util.List;


public class ErrorResponse {

    private final Instant timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final List<String> errors;
    private final String path;

    public ErrorResponse(Instant timestamp, int status, String error, String message, List<String> errors, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.errors = errors;
        this.path = path;
    }


    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public List<String> getErrors() {
        return errors;
    }

}
