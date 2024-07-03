package com.example.myapplication.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Teacher extends User implements Serializable {
    private List<String> assignedTaskIds;

    // Constructor
    public Teacher(String userId, String name,String email, String password,int profilePic) {
        super(userId, name, email, password,profilePic);
        this.assignedTaskIds = assignedTaskIds;
    }

    public Teacher()
    {

    }
    // Getters and Setters
    public List<String> getAssignedTaskIds() {
        return assignedTaskIds;
    }

    public void assignTaskId(String taskId) {
        this.assignedTaskIds.add(taskId);
    }

    public void removeAssignedTaskId(String taskId) {
        this.assignedTaskIds.remove(taskId);
    }

    @Override
    public String getRole() {
        return "Teacher";
    }
}

