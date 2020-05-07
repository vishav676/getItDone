package com.vishavlakhtia.nearby.outline;

public class tasks {

    String title,date,taskID,time;
    String description;

    String price;

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public tasks(String title, String date, String taskID, String time, String description, String price) {
        this.title = title;
        this.date = date;
        this.taskID = taskID;
        this.time = time;
        this.description = description;
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public tasks(){}

}
