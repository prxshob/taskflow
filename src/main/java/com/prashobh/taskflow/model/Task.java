package com.prashobh.taskflow.model;

import java.time.Instant;

public class Task {
    private Long id;
    private String title;
    private boolean completed;
    private Instant createdAt = Instant.now();

    public Task() {

    }

    public Task(Long id, String title, boolean completed) {
        this.id= id;
        this.title= title;
        this.completed= completed;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

        @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}
