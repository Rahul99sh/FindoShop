package com.pro.findoshop.dataClasses;

import com.google.firebase.Timestamp;

public class Promotion {
    String promoId,orderId, storeId;
    int amount;
    boolean paymentStatus;
    Timestamp startDate, endDate;
    String imageUrl;

    public Promotion(){

    }

    public Promotion(String promoId, String orderId, String storeId, int amount, boolean paymentStatus, Timestamp startDate, Timestamp endDate, String imageUrl) {
        this.promoId = promoId;
        this.orderId = orderId;
        this.storeId = storeId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
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

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
