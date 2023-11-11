package com.pro.findoshop.dataClasses;

public class PromoMeta {
    int duration,price;
    public PromoMeta(){};

    public PromoMeta(int duration, int price) {
        this.duration = duration;
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
