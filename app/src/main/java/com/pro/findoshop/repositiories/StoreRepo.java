package com.pro.findoshop.repositiories;

import androidx.lifecycle.MutableLiveData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.pro.findoshop.dataClasses.Items;
import com.pro.findoshop.dataClasses.Store;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StoreRepo {
    FirebaseFirestore mFirestore;
    MutableLiveData<Store> storeMutableLiveData;
    MutableLiveData<List<Items>> storeItemsMutableLiveData;
    String storeId;

    public StoreRepo(String storeId) {
        this.storeMutableLiveData = new MutableLiveData<>();
        this.storeItemsMutableLiveData = new MutableLiveData<>();
        mFirestore = FirebaseFirestore.getInstance();
        this.storeId = storeId;
    }

    public MutableLiveData<Store> getStoreMutableLiveData() {
        mFirestore.collection("Store").document(storeId).addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {
                Store store = value.toObject(Store.class);
                storeMutableLiveData.postValue(store);
            }
        });
        return storeMutableLiveData;
    }
    public MutableLiveData<List<Items>> getStoreItemsMutableLiveData() {
        mFirestore.collection("Store").document(storeId).collection("Items").addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {
                List<Items> itemList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc != null) {
                        itemList.add(doc.toObject(Items.class));
                    }
                }
                storeItemsMutableLiveData.postValue(itemList);
            }
        });
        return storeItemsMutableLiveData;
    }
}
