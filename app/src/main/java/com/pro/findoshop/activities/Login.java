package com.pro.findoshop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.pro.findoshop.MainActivity;
import com.pro.findoshop.R;
import com.pro.findoshop.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.login.setOnClickListener(view -> {
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
            finish();
        });
        binding.newAccount.setOnClickListener(view -> {
            Intent i = new Intent(Login.this, Registration.class);
            startActivity(i);
            finish();
        });
    }
}