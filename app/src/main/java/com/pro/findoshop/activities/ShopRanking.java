package com.pro.findoshop.activities;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pro.findoshop.R;
import com.pro.findoshop.adapters.RankingAdapter;
import com.pro.findoshop.dataClasses.Store;
import com.pro.findoshop.databinding.ActivityShopRankingBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ShopRanking extends AppCompatActivity {

    Store store;
    FirebaseFirestore db;
    List<String> allStoresId;
    List<Store> allStores;
    long count = 0;
    ActivityShopRankingBinding binding;
    RankingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_shop_ranking);
        db = FirebaseFirestore.getInstance();
        allStores = new ArrayList<>();
        this.store = getIntent().getParcelableExtra("store");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(store.getStoreLat(), store.getStoreLong(), 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
//                String area = address.getSubLocality();
                String city = address.getLocality();
                String state = address.getAdminArea();
                db.collection("StoreList").document(state).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            allStoresId = (List<String>) documentSnapshot.get(city);
                            getStore();
                        }
                    }
                });


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getStore() {
        long size = allStoresId.size();
        for(String id : allStoresId){
            db.collection("Store").document(id).get().addOnSuccessListener(documentSnapshot -> {
                if(documentSnapshot.exists()) {
                    Store s = documentSnapshot.toObject(Store.class);
                    assert s != null;
                    allStores.add(s);
                }
                count++;

                if (count == size) {
                    generateRanks();
                }
            });
        }
    }

    private void generateRanks() {
        Collections.sort(allStores, new Comparator<Store>() {
            @Override
            public int compare(Store store1, Store store2) {
                // Compare by rating first
                int ratingComparison = compareNullableDoubles(store2.getRating(), store1.getRating());
                if (ratingComparison != 0) {
                    return ratingComparison;
                }

                // If ratings are equal, compare by visits
                return Integer.compare(store2.getVisits(), store1.getVisits());
            }

            // Helper method to compare nullable doubles
            private int compareNullableDoubles(Double d1, Double d2) {
                if (d1 == null && d2 == null) {
                    return 0;
                }
                if (d1 == null) {
                    return -1;
                }
                if (d2 == null) {
                    return 1;
                }
                return Double.compare(d1, d2);
            }
        });
        binding.myRank.setText(String.valueOf(findStoreRank(store.getStoreId())));
        binding.myRating.setText(String.valueOf(store.getRating()));
        binding.myVisits.setText(String.valueOf(store.getVisits()));
        binding.myName.setText(String.valueOf(store.getStoreName()));
        Glide.with(this).load(store.getStoreUrl()).into(binding.myImg);
        adapter = new RankingAdapter(allStores,this,store.getStoreId());
        binding.rankRv.setAdapter(adapter);
    }

    private int findStoreRank(String targetStoreId) {
        for (int i = 0; i < allStores.size(); i++) {
            if (allStores.get(i).getStoreId().equals(targetStoreId)) {
                return i + 1; // Adding 1 to convert from zero-based index to rank
            }
        }
        return -1; // Store not found
    }
}