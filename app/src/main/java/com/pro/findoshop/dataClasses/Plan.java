package com.pro.findoshop.dataClasses;

public class Plan {
    int price,plan,duration;
    String name;

    public Plan(){}
    public Plan(int price, int plan, int duration, String name) {
        this.price = price;
        this.plan = plan;
        this.duration = duration;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
