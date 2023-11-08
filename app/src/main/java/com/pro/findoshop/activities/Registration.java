package com.pro.findoshop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.pro.findoshop.MainActivity;
import com.pro.findoshop.R;
import com.pro.findoshop.databinding.ActivityRegistrationBinding;

public class Registration extends AppCompatActivity {

    ActivityRegistrationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        binding.register.setOnClickListener(view -> {
            Intent i = new Intent(Registration.this, MainActivity.class);
            startActivity(i);
            finish();
        });
        binding.login.setOnClickListener(view -> {
            Intent i = new Intent(Registration.this, Login.class);
            startActivity(i);
            finish();
        });
    }
}