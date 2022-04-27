package com.example.attendancetracker;

public class MonitoringModel {

    private String name,role, team;

    public MonitoringModel(String name, String role,  String team   ) {
        this.name = name;
        this.role = role;
        this.team = team;

    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getTeam() {
        return team;
    }


}
