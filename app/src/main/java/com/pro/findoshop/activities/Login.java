package com.pro.findoshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pro.findoshop.MainActivity;
import com.pro.findoshop.R;
import com.pro.findoshop.bottomSheets.LoadingSheet;
import com.pro.findoshop.databinding.ActivityLoginBinding;

import java.io.Console;
import java.util.HashMap;
import java.util.Objects;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth mAuth;
    LoadingSheet loadingSheet;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingSheet = new LoadingSheet("Verifying Details...");
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.login.setOnClickListener(view -> {
            loginUser();
        });
        binding.newAccount.setOnClickListener(view -> {
            Intent i = new Intent(Login.this, Registration.class);
            startActivity(i);
            finish();
        });
    }

    private void loginUser() {
        String email = Objects.requireNonNull(binding.email.getText()).toString();
        String password = Objects.requireNonNull(binding.password.getText()).toString();

        if (TextUtils.isEmpty(email)) {
            binding.emailBox.setError("Email cannot be empty");
            binding.email.requestFocus();
        }else if (TextUtils.isEmpty(password)) {
            binding.passwordBox.setError("Password cannot be empty");
            binding.password.requestFocus();
        }else{
            loadingSheet.show(getSupportFragmentManager(), loadingSheet.getTag());
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    db.collection("ShopOwners").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).get().addOnSuccessListener(documentSnapshot -> {
                        loadingSheet.dismiss();
                        if(documentSnapshot.exists()){
                            FirebaseMessaging.getInstance().getToken()
                                    .addOnCompleteListener(task1 -> {
                                        if (!task1.isSuccessful()) {
                                            return;
                                        }
                                        // Get new FCM registration token
                                        String registrationToken = task1.getResult();
                                        Log.d("token",registrationToken);
                                        db.collection("ShopOwners").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).update("deviceToken", registrationToken);

                                    });
                            Intent i = new Intent(Login.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(Login.this, "This Account is not associated with Findo Shop", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                        }
                    }).addOnFailureListener(e -> {
                        loadingSheet.dismiss();
                        Toast.makeText(Login.this, "This Account is not associated with Findo Shop", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                    });
                } else {
                    Toast.makeText(Login.this, "Log in Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}