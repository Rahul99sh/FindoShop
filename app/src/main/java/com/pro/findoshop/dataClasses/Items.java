package com.pro.findoshop.dataClasses;

import java.util.List;

public class Items {

    private List<String> imageUrls;
    private String itemName, itemDesc;
    private double itemRating;

    public Items(){}
    public Items(List<String> imageUrls, String itemName, String itemDesc, double itemRating) {
        this.imageUrls = imageUrls;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.itemRating = itemRating;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public double getItemRating() {
        return itemRating;
    }

    public void setItemRating(double itemRating) {
        this.itemRating = itemRating;
    }
}
