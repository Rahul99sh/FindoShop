package com.pro.findoshop.viewModels;

import static com.pro.findoshop.MainActivity.storeId;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.pro.findoshop.dataClasses.Items;
import com.pro.findoshop.dataClasses.Store;
import com.pro.findoshop.repositiories.StoreRepo;

import java.util.List;

public class StoreViewModel extends ViewModel {
    MutableLiveData<Store> storeMutableLiveData;
    MutableLiveData<List<Items>> storeItemsMutableLiveData;
    FirebaseFirestore mFirestore;
    StoreRepo storeRepository;

    public StoreViewModel() {
        storeRepository = new StoreRepo(storeId);
        storeMutableLiveData=  storeRepository.getStoreMutableLiveData();
        storeItemsMutableLiveData=  storeRepository.getStoreItemsMutableLiveData();
        mFirestore = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<Store> getLiveStoreData() {
        return storeMutableLiveData;
    }
    public MutableLiveData<List<Items>> getLiveStoreItemsData() {
        return storeItemsMutableLiveData;
    }
}