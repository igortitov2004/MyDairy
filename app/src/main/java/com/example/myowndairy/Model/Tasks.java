package com.example.myowndairy.Model;

import java.sql.Time;
import java.util.Date;

public class Tasks {
    public String getHeading() {

        return heading;
    }

    public String getDate() {

        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String heading;



    int id;

    public String date;
    String time;

    String description;

    public int getIsDone() {
        return isDone;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
    }

    int isDone;

    public Tasks(int id,String heading,String date,String time,String description) {
        this.id = id;
        this.heading = heading;
        this.date = date;
        this.time = time;
        this.description = description;

    }

    public Tasks(int id,String heading,String date,String time,String description,int isDone) {
        this.id = id;
        this.heading = heading;
        this.date = date;
        this.time = time;
        this.description = description;
        this.isDone = isDone;
    }

    public Tasks() {
    }


}
