package com.example.myapplication.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class TaskSubmission {
    private String submissionId;  // Combination of studentId and taskId
    private String taskId;
    private String studentId;
    private String status;  // Assigned, Submitted, Marked
    private Integer marks;

    private String submissionTime;

    private String markedTime;

    // Empty constructor required for Firestore serialization
    public TaskSubmission() {}

    // Constructor with parameters
    public TaskSubmission(String submissionId, String taskId, String studentId, String status, Integer marks, String submissionTime, String markedTime) {
        this.submissionId = submissionId;
        this.taskId = taskId;
        this.studentId = studentId;
        this.status = status;
        this.marks = marks;
        this.submissionTime = submissionTime;
        this.markedTime = markedTime;
    }

    // Getters and Setters
    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public String getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getMarkedTime() {
        return markedTime;
    }

    public void setMarkedTime(String markedTime) {
        this.markedTime = markedTime;
    }

    @Override
    public String toString() {
        return "TaskSubmission{" +
                "submissionId='" + submissionId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", status='" + status + '\'' +
                ", marks=" + marks +
                ", submissionTime=" + submissionTime +
                ", markedTime=" + markedTime +
                '}';
    }
}
