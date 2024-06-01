package com.example.myapplication.models;

public class Progress {
    private String progressId;
    private String taskId;
    private String studentId;
    private String statusUpdate;
    private String timestamp;

    // Constructor
    public Progress(String progressId, String taskId, String studentId, String statusUpdate, String timestamp) {
        this.progressId = progressId;
        this.taskId = taskId;
        this.studentId = studentId;
        this.statusUpdate = statusUpdate;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getProgressId() {
        return progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStatusUpdate() {
        return statusUpdate;
    }

    public void setStatusUpdate(String statusUpdate) {
        this.statusUpdate = statusUpdate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

