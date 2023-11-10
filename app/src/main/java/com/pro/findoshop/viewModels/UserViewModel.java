package com.pro.findoshop.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.pro.findoshop.dataClasses.User;
import com.pro.findoshop.repositiories.UserRepo;

public class UserViewModel extends ViewModel {
    MutableLiveData<User> userMutableLiveData;
    FirebaseFirestore mFirestore;
    UserRepo userRepository;

    public UserViewModel() {
        userRepository = new UserRepo();
        userMutableLiveData=  userRepository.getUserMutableLiveData();
        mFirestore = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<User> getLiveUserData() {
        return userMutableLiveData;
    }
}