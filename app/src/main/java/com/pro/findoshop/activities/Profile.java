package com.pro.findoshop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.pro.findoshop.R;
import com.pro.findoshop.dataClasses.User;
import com.pro.findoshop.databinding.ActivityProfileBinding;
import com.pro.findoshop.viewModels.UserViewModel;

public class Profile extends AppCompatActivity {

    User user;
    ActivityProfileBinding binding;
    private UserViewModel userViewModel;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getLiveUserData().observe(this, user -> {
            this.user = user;
            binding.name.setText(user.getOwnerName());
            binding.email.setText(user.getEmail());
        });
        mAuth = FirebaseAuth.getInstance();

        binding.logout.setOnClickListener(v -> {

        });
        binding.contactUs.setOnClickListener(v -> {
            startActivity(new Intent(this, ContactUs.class));
        });
        binding.registerShop.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterShop.class));
            finish();
        });
    }
}