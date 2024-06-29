package com.example.myapplication.models;


import java.util.List;

public class Task {

    private int task_icon;
    private String task_title;
    private String due_date;
    private String assignedDate;
    private String status;
    private int status_icon;
    private String task_description;
    private String taskId;
    private boolean isCompleted ;
    private String assignedByTeacherId;
    private List<String> assignedToStudentIds;

    public Task(int icon,String task_title,String task_description, String date,
                String assignedDate, int status,String assignedByTeacherId)
    {
        this.task_icon= icon;
        this.task_title = task_title;
        this.due_date = date;
        this.status_icon = status;
        this.task_description = task_description;
        this.assignedDate = assignedDate;
        this.assignedByTeacherId = assignedByTeacherId;
        isCompleted = false;
    }

    public String getAssignedByTeacherId() {
        return assignedByTeacherId;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public String getDue_date() {
        return due_date;
    }

    public String getTaskId() {
        return taskId;
    }


    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public void setAssignedToStudentIds(List<String> assignedToStudentIds) {
        this.assignedToStudentIds = assignedToStudentIds;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public int getTask_icon() {
        return task_icon;
    }

    public String getTask_title() {
        return task_title;
    }

    public int getStatus_icon() {
        return status_icon;
    }

    public String getTask_date() {
        return due_date;
    }

    public void setStatus_icon(int status_icon) {
        this.status_icon = status_icon;
    }

    public void setTask_date(String task_date) {
        this.due_date = task_date;
    }

    public void setTask_icon(int task_icon) {
        this.task_icon = task_icon;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }
    public String getDescription() {
        return this.task_description;
    }

    public void setDescription(String description) {
        this.task_description = description;
    }
    public void setAssignedByTeacherId(String assignedByTeacherId) {
        this.assignedByTeacherId = assignedByTeacherId;
    }

    public List<String> getAssignedToStudentIds() {
        return assignedToStudentIds;
    }

    public void assignToStudentId(String studentId) {
        this.assignedToStudentIds.add(studentId);
    }

    public void removeAssignedStudentId(String studentId) {
        this.assignedToStudentIds.remove(studentId);
    }
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void markAsDone()
    {
        isCompleted = true;
    }
    public boolean isCompleted()
    {
        return isCompleted;
    }

    public void deleteTask()
    {

    }
    public boolean attachFile(String filepath)
    {
        return true;
    }
}
