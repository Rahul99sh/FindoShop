package com.pro.findoshop.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pro.findoshop.R;
import com.pro.findoshop.adapters.PromoAdapter;
import com.pro.findoshop.bottomSheets.AddPromo;
import com.pro.findoshop.dataClasses.Promotion;
import com.pro.findoshop.dataClasses.Store;
import com.pro.findoshop.databinding.ActivityManagePromotionsBinding;
import com.razorpay.PaymentResultListener;

import java.util.ArrayList;
import java.util.List;

public class ManagePromotions extends AppCompatActivity implements PaymentResultListener {

    FirebaseFirestore db;
    ActivityManagePromotionsBinding binding;
    Store store;
    int currentPromoIds = 0;
    AddPromo addPromo;
    List<Promotion> promotionList;
    PromoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        store = getIntent().getParcelableExtra("store");
        promotionList = new ArrayList<>();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_manage_promotions);
        db = FirebaseFirestore.getInstance();
        binding.floatingActionButton.setOnClickListener(v -> {
            addPromo = AddPromo.newInstance(store);
            addPromo.show(getSupportFragmentManager(), addPromo.getTag());
        });

        List<String> promoIds = store.getPromoIds();
        int totalPromoIds = promoIds.size();
        for (String id : promoIds) {
            db.collection("Promotion").document(id).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        promotionList.add(documentSnapshot.toObject(Promotion.class));
                        currentPromoIds++;

                        if (currentPromoIds == totalPromoIds) {
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    });
        }

        adapter = new PromoAdapter(promotionList,this);
        binding.promoRv.setAdapter(adapter);
    }

    @Override
    public void onPaymentSuccess(String s) {
        // this method is called on payment success.
        addPromo.uploadImage(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        // on payment failed.
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }

}