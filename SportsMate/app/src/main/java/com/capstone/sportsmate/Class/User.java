package com.capstone.sportsmate.Class;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id; //key
    private String name, gender, zipCode, badminton, basketball, tennis;
    private List<String> ticketID = new ArrayList<>();;


    public User(){}

    public void setUid(String uid){ this.id = uid; }
    public void setName(String name){
        this.name = name;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public void setZipCode(String zipCode){
        this.zipCode = zipCode;
    }
    public void setBadminton(String badminton){
        this.badminton = badminton;
    }
    public void setBasketball(String basketball){
        this.basketball = basketball;
    }
    public void setTennis(String tennis){
        this.tennis = tennis;
    }
    public void addTicket(String ticketID) {this.ticketID.add(ticketID); }
    public void removeTicket(String ticketID) {this.ticketID.remove(ticketID); }

    public String getUid(){return this.id;}
    public String getName(){return this.name;}
    public String getGender(){return this.gender;}
    public String getZipCode(){return this.zipCode;}
    public String getBadminton(){return this.badminton;}
    public String getBasketball(){return this.basketball;}
    public String getTennis(){return this.tennis;}
    public List<String> getTickets(){return this.ticketID;}

}
