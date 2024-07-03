package com.example.myapplication.models;

public abstract class User {
    protected String userId;
    protected String name;
    protected String email;
    protected String password;
    protected int profilePic;

    // Constructors
    public User(String userId, String name, String email, String password,int profilePic) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
    }

    public User(){

    }
    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    public int getProfilePic() {
        return profilePic;
    }

    // Abstract methods (to be implemented by subclasses)
    public abstract String getRole();
}

