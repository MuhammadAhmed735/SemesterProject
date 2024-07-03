package com.example.myapplication.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Task implements Parcelable {


    private String task_title;
    private String due_date;
    private String assignedDate;
    private String status;

    private String task_description;
    private String taskId;
    private boolean isCompleted ;
    private String assignedByTeacherId;
    private List<String> assignedToStudentIds;

    public Task(String task_title,String task_description, String date,
                String assignedDate,String assignedByTeacherId)
    {

        this.task_title = task_title;
        this.due_date = date;

        this.task_description = task_description;
        this.assignedDate = assignedDate;
        this.assignedByTeacherId = assignedByTeacherId;
        isCompleted = false;
    }


    protected Task(Parcel in) {

        task_title = in.readString();
        due_date = in.readString();
        assignedDate = in.readString();
        status = in.readString();
        task_description = in.readString();
        taskId = in.readString();
        isCompleted = in.readByte() != 0;
        assignedByTeacherId = in.readString();
        assignedToStudentIds = in.createStringArrayList();
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
        dest.writeString(assignedByTeacherId);
        dest.writeStringList(assignedToStudentIds);
    }

    @Override
    public int describeContents() {
        return 0;
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




    public String getTask_title() {
        return task_title;
    }



    public String getTask_date() {
        return due_date;
    }



    public void setTask_date(String task_date) {
        this.due_date = task_date;
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
