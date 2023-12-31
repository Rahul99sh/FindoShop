package com.pro.findoshop.repositiories;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pro.findoshop.dataClasses.User;
import java.util.Objects;

public class UserRepo {
        MutableLiveData<User> userMutableLiveData1;
        FirebaseFirestore mFirestore;
        FirebaseAuth mAuth;
        MutableLiveData<User> userMutableLiveData;

    public UserRepo() {
        this.userMutableLiveData1 = new MutableLiveData<>();
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userMutableLiveData = new MutableLiveData<>();

    }
    public MutableLiveData<User> getUserMutableLiveData() {
        Log.i("TAG", "getBlogListMutableLiveData: ");
        mFirestore.collection("ShopOwners")
                .document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                .addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {
                User user = value.toObject(User.class);
                userMutableLiveData1.postValue(user);
            }
        });
        return userMutableLiveData1;
    }

    }
