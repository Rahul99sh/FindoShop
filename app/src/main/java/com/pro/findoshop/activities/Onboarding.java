package com.pro.findoshop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pro.findoshop.R;
import com.pro.findoshop.databinding.ActivityOnboardingBinding;

public class Onboarding extends AppCompatActivity {

    ActivityOnboardingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding);
        binding.login.setOnClickListener(view -> {
            Intent i = new Intent(Onboarding.this, Login.class);
            startActivity(i);
            finish();
        });
        binding.register.setOnClickListener(view -> {
            Intent i = new Intent(Onboarding.this, Registration.class);
            startActivity(i);
            finish();
        });
    }
}