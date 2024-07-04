package com.example.myapplication.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Project implements Parcelable {


    private String project_title;
    private String due_date;
    private String assignedDate;
    private String status;

    private String teacherId;
    private String project_description;
    private String projectId;
    private boolean isCompleted ;
    private String assignedByTeacherName;
    private List<String> assignedToStudentIds;

    public Project(String projectId,String project_title,String project_description, String date,
                String assignedDate,String assignedByTeacherName,String teacherId)
    {

        this.projectId = projectId;
        this.project_title = project_title;
        this.due_date = date;

        this.teacherId = teacherId;
        this.project_description = project_description;
        this.assignedDate = assignedDate;
        this.assignedByTeacherName = assignedByTeacherName;
        isCompleted = false;
    }


    protected Project(Parcel in) {

        project_title = in.readString();
        due_date = in.readString();
        assignedDate = in.readString();
        status = in.readString();
        project_description = in.readString();
        projectId = in.readString();
        isCompleted = in.readByte() != 0;
        assignedByTeacherName = in.readString();
        teacherId = in.readString();

    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(project_title);
        dest.writeString(due_date);
        dest.writeString(assignedDate);
        dest.writeString(status);


        dest.writeString(project_description);
        dest.writeString(projectId);
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

    public String getprojectId() {
        return projectId;
    }


    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }



    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public void setprojectId(String projectId) {
        this.projectId = projectId;
    }




    public String getproject_title() {
        return project_title;
    }







    public void setproject_title(String project_title) {
        this.project_title = project_title;
    }
    public String getDescription() {
        return this.project_description;
    }

    public void setDescription(String description) {
        this.project_description = description;
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

    public void deleteproject()
    {

    }
    public boolean attachFile(String filepath)
    {
        return true;
    }
}
