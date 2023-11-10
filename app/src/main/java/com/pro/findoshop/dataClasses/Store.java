package com.pro.findoshop.dataClasses;

public class Store {
    private String StoreName;
    private Double StoreLat;
    private Double StoreLong;
    private String StoreUrl;
    private String OwnerId,address,gstin,licenceNo;
    private String StoreId;
    public Store() {}

    public Store(String storeName, Double storeLat, Double storeLong, String storeUrl, String ownerId, String address, String gstin, String licenceNo, String storeId) {
        StoreName = storeName;
        StoreLat = storeLat;
        StoreLong = storeLong;
        StoreUrl = storeUrl;
        OwnerId = ownerId;
        this.address = address;
        this.gstin = gstin;
        this.licenceNo = licenceNo;
        StoreId = storeId;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public Double getStoreLat() {
        return StoreLat;
    }

    public void setStoreLat(Double storeLat) {
        StoreLat = storeLat;
    }

    public Double getStoreLong() {
        return StoreLong;
    }

    public void setStoreLong(Double storeLong) {
        StoreLong = storeLong;
    }

    public String getStoreUrl() {
        return StoreUrl;
    }

    public void setStoreUrl(String storeUrl) {
        StoreUrl = storeUrl;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }
}


