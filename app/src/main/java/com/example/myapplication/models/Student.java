package com.example.myapplication.models;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<String> taskIds;
    private List<String> enrolledCoursesIds;

    // Constructor
    public Student(String userId, String name,String username, String email, String password,int profilePic,
    List<String> enrolledCoursesIds,List<String> taskIds) {
        super(userId, name,username, email, password,profilePic);
        this.taskIds =  taskIds;
        this.enrolledCoursesIds = enrolledCoursesIds;
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

    public List<String> getEnrolledCoursesIds() {
        return enrolledCoursesIds;
    }
    public List<Task> viewAssignedTasks()
    {
        return null;
    }
    public Boolean submitTask()
    {
        return true;
    }

    @Override
    public String getRole() {
        return "Student";
    }
}

