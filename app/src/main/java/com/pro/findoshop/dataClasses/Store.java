package com.pro.findoshop.dataClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Store implements Parcelable {
    List<String> promoIds;
    private String StoreName;
    private Double StoreLat;
    private Double StoreLong;
    private String StoreUrl;
    private String OwnerId,address,gstin,licenceNo;
    private String StoreId;
    public Store() {}

    public Store(List<String> promoIds, String storeName, Double storeLat, Double storeLong, String storeUrl, String ownerId, String address, String gstin, String licenceNo, String storeId) {
        this.promoIds = promoIds;
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

    public List<String> getPromoIds() {
        return promoIds;
    }

    public void setPromoIds(List<String> promoIds) {
        this.promoIds = promoIds;
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

    protected Store(Parcel in) {
        promoIds = in.createStringArrayList();
        StoreName = in.readString();
        if (in.readByte() == 0) {
            StoreLat = null;
        } else {
            StoreLat = in.readDouble();
        }
        if (in.readByte() == 0) {
            StoreLong = null;
        } else {
            StoreLong = in.readDouble();
        }
        StoreUrl = in.readString();
        OwnerId = in.readString();
        address = in.readString();
        gstin = in.readString();
        licenceNo = in.readString();
        StoreId = in.readString();
    }

    public static final Creator<Store> CREATOR = new Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(promoIds);
        dest.writeString(StoreName);
        if (StoreLat == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(StoreLat);
        }
        if (StoreLong == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(StoreLong);
        }
        dest.writeString(StoreUrl);
        dest.writeString(OwnerId);
        dest.writeString(address);
        dest.writeString(gstin);
        dest.writeString(licenceNo);
        dest.writeString(StoreId);
    }
}


