package com.pro.findoshop.dataClasses;

import com.google.firebase.Timestamp;

import java.util.List;

public class User {
    private String ownerName, email;
    private Timestamp startDate,endDate;
    private int plan;
    private List<String> stores;

    public User(){}

    public User(String ownerName, String email, Timestamp startDate, Timestamp endDate, int plan, List<String> stores) {
        this.ownerName = ownerName;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.plan = plan;
        this.stores = stores;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public List<String> getStores() {
        return stores;
    }

    public void setStores(List<String> stores) {
        this.stores = stores;
    }
}
