package com.example.myapplication.models;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String projectId;
    private String title;
    private String description;
    private List<String> taskIds;

    // Constructor
    public Project(String projectId, String title, String description) {
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.taskIds = new ArrayList<>();
    }

    // Getters and Setters
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTaskIds() {
        return taskIds;
    }

    public void addTaskId(String taskId) {
        this.taskIds.add(taskId);
    }

    public void removeTaskId(String taskId) {
        this.taskIds.remove(taskId);
    }
}

