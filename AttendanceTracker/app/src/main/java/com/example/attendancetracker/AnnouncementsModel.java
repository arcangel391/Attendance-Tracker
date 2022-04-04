package com.example.attendancetracker;

public class AnnouncementsModel {
   private String title,date,time;

   public AnnouncementsModel(String title, String date, String time){
       this.title = title;
       this.date = date;
       this.time = time;
   }

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
