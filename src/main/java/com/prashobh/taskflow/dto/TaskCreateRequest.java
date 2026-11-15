package com.prashobh.taskflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskCreateRequest {
    @NotBlank(message = "Title cannot be empty!")
    private String title;
    @NotNull(message = "Completed Status should be provided!")
    private Boolean completed;

    public TaskCreateRequest() {
    }

    public String getTitle() {
        return title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
