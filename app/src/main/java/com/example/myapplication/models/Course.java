package com.example.myapplication.models;

public class Course {
    private String courseId;
    private String courseTitle;
    private int semester;
    private String instructorId;
    private int duration_in_weeks;

    public Course(String courseId,String courseTitle , int semester, String instructorId,
                  int duration_in_weeks)
    {

        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.semester = semester;
        this.instructorId = instructorId;
        this.duration_in_weeks = duration_in_weeks;
    }

    public int getDuration_in_weeks() {
        return duration_in_weeks;
    }

    public String getCourseId() {
        return courseId;
    }

    public int getSemester() {
        return semester;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setDuration_in_weeks(int duration_in_weeks) {
        this.duration_in_weeks = duration_in_weeks;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

}
