package com.capstone.sportsmate.Class;

import java.util.ArrayList;
import java.util.List;

public class Ticket {

    private String id; //key
    private String sports, level, zipCode, date, time;
    private List<String> userID = new ArrayList<>();

    public Ticket(){}

    public void setTid(String tid) { this.id = tid; }
    public void setSports(String sports) { this.sports = sports; }
    public void setLevel(String level) { this.level = level; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void addUser(String userID) {this.userID.add(userID); }
    public void removeUser(String userID) {this.userID.remove(userID); }

    public String getTid() { return this.id; }
    public String getSports() { return this.sports; }
    public String getLevel() { return this.level; }
    public String getZipCode() { return this.zipCode; }
    public String getDate() { return this.date; }
    public String getTime() {return this.time; }
    public List<String> getUserID() { return this.userID; }

}
