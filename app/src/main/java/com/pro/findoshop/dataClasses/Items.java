package com.pro.findoshop.dataClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Items implements Parcelable {

    private String ItemUrl;
    private String ItemName, ItemDescription,price,ItemId,Category;
    private double ItemRating;
    private int clicks,addedToCart;

    public Items(){}

    protected Items(Parcel in) {
        ItemUrl = in.readString();
        ItemName = in.readString();
        ItemDescription = in.readString();
        price = in.readString();
        ItemId = in.readString();
        Category = in.readString();
        ItemRating = in.readDouble();
        clicks = in.readInt();
        addedToCart = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ItemUrl);
        dest.writeString(ItemName);
        dest.writeString(ItemDescription);
        dest.writeString(price);
        dest.writeString(ItemId);
        dest.writeString(Category);
        dest.writeDouble(ItemRating);
        dest.writeInt(clicks);
        dest.writeInt(addedToCart);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Items> CREATOR = new Creator<Items>() {
        @Override
        public Items createFromParcel(Parcel in) {
            return new Items(in);
        }

        @Override
        public Items[] newArray(int size) {
            return new Items[size];
        }
    };

    public Items(String itemUrl, String itemName, String itemDescription, String price, String itemId, String category, double itemRating, int clicks, int addedToCart) {
        ItemUrl = itemUrl;
        ItemName = itemName;
        ItemDescription = itemDescription;
        this.price = price;
        ItemId = itemId;
        Category = category;
        ItemRating = itemRating;
        this.clicks = clicks;
        this.addedToCart = addedToCart;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getAddedToCart() {
        return addedToCart;
    }

    public void setAddedToCart(int addedToCart) {
        this.addedToCart = addedToCart;
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
