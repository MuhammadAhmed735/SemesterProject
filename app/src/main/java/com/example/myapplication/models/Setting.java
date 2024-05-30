package com.example.myapplication.models;

public class Setting {

    private int icon;
    private String name;
    private String description;
    private int iconTint;
    private int iconResourceId;

    public Setting(String name, String description, int iconTint, int iconResourceId) {
       // this.icon = icon;
        this.name = name;
        this.description = description;
        this.iconTint = iconTint;
        this.iconResourceId = iconResourceId;
    }
/*
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
*/
    public String getTitleName() {
        return name;
    }

    public void setTitleName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIconTint() {
        return iconTint;
    }

    public void setIconTint(int iconTint) {
        this.iconTint = iconTint;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }
}

