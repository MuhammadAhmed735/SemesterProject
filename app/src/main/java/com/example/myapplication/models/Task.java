package com.example.myapplication.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Task implements Parcelable {


    private String task_title;
    private String due_date;
    private String assignedDate;
    private String status;

    private String teacherId;
    private String task_description;
    private String taskId;
    private boolean isCompleted ;
    private String assignedByTeacherName;
    private List<String> assignedToStudentIds;

    public Task(String taskId,String task_title,String task_description, String date,
                String assignedDate,String assignedByTeacherName,String teacherId)
    {

        this.taskId = taskId;
        this.task_title = task_title;
        this.due_date = date;

        this.teacherId = teacherId;
        this.task_description = task_description;
        this.assignedDate = assignedDate;
        this.assignedByTeacherName = assignedByTeacherName;
        isCompleted = false;
    }
    public Task()
    {

    }


    protected Task(Parcel in) {

        task_title = in.readString();
        due_date = in.readString();
        assignedDate = in.readString();
        status = in.readString();
        task_description = in.readString();
        taskId = in.readString();
        isCompleted = in.readByte() != 0;
        assignedByTeacherName = in.readString();
        teacherId = in.readString();

    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(task_title);
        dest.writeString(due_date);
        dest.writeString(assignedDate);
        dest.writeString(status);


        dest.writeString(task_description);
        dest.writeString(taskId);
        dest.writeByte((byte) (isCompleted ? 1 : 0));
        dest.writeString(assignedByTeacherName);
        dest.writeString(teacherId);

    }

    @Override
    public int describeContents() {
        return 0;
    }
    public String getAssignedByTeacherName() {
        return assignedByTeacherName;
    }

    public String getTeacherId()
    {
        return this.teacherId;
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



    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }




    public String getTask_title() {
        return task_title;
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
    public void setAssignedByTeacherName(String assignedByTeacherName) {
        this.assignedByTeacherName = assignedByTeacherName;
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
