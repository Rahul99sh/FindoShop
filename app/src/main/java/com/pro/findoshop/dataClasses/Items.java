package com.pro.findoshop.dataClasses;

import java.util.List;

public class Items {

    private String ItemUrl;
    private String ItemName, ItemDescription,price,ItemId,Category;
    private double ItemRating;

    public Items(){}

    public Items(String itemUrl, String itemName, String itemDescription, String price, String itemId, String category, double itemRating) {
        ItemUrl = itemUrl;
        ItemName = itemName;
        ItemDescription = itemDescription;
        this.price = price;
        ItemId = itemId;
        Category = category;
        ItemRating = itemRating;
    }

    public String getItemUrl() {
        return ItemUrl;
    }

    public void setItemUrl(String itemUrl) {
        ItemUrl = itemUrl;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public double getItemRating() {
        return ItemRating;
    }

    public void setItemRating(double itemRating) {
        ItemRating = itemRating;
    }
}
