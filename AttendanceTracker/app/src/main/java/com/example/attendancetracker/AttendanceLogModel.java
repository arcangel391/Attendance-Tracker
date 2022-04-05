package com.example.attendancetracker;

public class AttendanceLogModel {
   private String date,day, timeIn, timeOut;

    public AttendanceLogModel(String date, String day,  String timeIn, String timeOut) {
        this.date = date;
        this.day = day;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }
}
