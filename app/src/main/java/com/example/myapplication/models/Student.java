package com.example.myapplication.models;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<String> taskIds;

    // Constructor
    public Student(String userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.taskIds = new ArrayList<>();
    }

    // Getters and Setters
    public List<String> getTaskIds() {
        return taskIds;
    }

    public void addTaskId(String taskId) {
        this.taskIds.add(taskId);
    }

    public void removeTaskId(String taskId) {
        this.taskIds.remove(taskId);
    }

    @Override
    public String getRole() {
        return "Student";
    }
}

