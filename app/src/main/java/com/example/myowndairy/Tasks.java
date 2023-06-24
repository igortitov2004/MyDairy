package com.example.myowndairy;

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

    String date;
    String time;

    String description;

    public Tasks(int id,String heading,String date,String time,String description) {
        this.id = id;
        this.heading = heading;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public Tasks() {


    }


}
