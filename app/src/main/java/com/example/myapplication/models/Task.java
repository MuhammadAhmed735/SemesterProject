package com.example.myapplication.models;


public class Task {

    private int task_icon;
    private String task_title;
    private String task_date;
    private int status_icon;

    public Task(int icon,String task_title, String date, int status)
    {
        this.task_icon= icon;
        this.task_title = task_title;
        this.task_date = date;
        this.status_icon = status;
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
        return task_date;
    }

    public void setStatus_icon(int status_icon) {
        this.status_icon = status_icon;
    }

    public void setTask_date(String task_date) {
        this.task_date = task_date;
    }

    public void setTask_icon(int task_icon) {
        this.task_icon = task_icon;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }
}
