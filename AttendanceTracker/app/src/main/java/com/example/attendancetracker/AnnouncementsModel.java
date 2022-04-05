package com.example.attendancetracker;

public class AnnouncementsModel {
   private String title,date,time,details;

   public AnnouncementsModel(String title, String date, String time, String details){
       this.title = title;
       this.date = date;
       this.time = time;
       this.details = details;
   }
    public String getDetails() { return details; }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
