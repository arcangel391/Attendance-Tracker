package com.example.attendancetracker;

public class AttendanceLogModel {
   private String date,day,hoursRendered, timeIn, timeOut, hoursLeft;

    public AttendanceLogModel(String date, String day, String hoursRendered, String timeIn, String timeOut, String hoursLeft) {
        this.date = date;
        this.day = day;
        this.hoursRendered = hoursRendered;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.hoursLeft = hoursLeft;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getHoursRendered() {
        return hoursRendered;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public String getHoursLeft() {
        return hoursLeft;
    }
}
