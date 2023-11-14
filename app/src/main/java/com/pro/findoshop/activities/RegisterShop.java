package com.pro.findoshop.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pro.findoshop.R;
import com.pro.findoshop.adapters.SliderAdapter;
import com.pro.findoshop.dataClasses.Plan;
import com.pro.findoshop.dataClasses.PromoMeta;
import com.pro.findoshop.databinding.ActivityRegisterShopBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator3;

public class RegisterShop extends AppCompatActivity implements PaymentResultListener {

    ActivityRegisterShopBinding binding;
    FirebaseFirestore db;
    List<Plan> planList;
    List<String> durationList, imageList;
    private SliderAdapter sliderAdapter;
    FirebaseAuth mAuth;
    private Handler handler;
    int finalAmount,duration;
    private ArrayAdapter<String> adapter;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    Checkout checkout;
    Uri selectedImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register_shop);
        db= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        setupSlideBar();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        planList = new ArrayList<>();
        durationList = new ArrayList<>();
        fetchDurationOptionsFromFirebase();
        Checkout.preload(getApplicationContext());
        checkout = new Checkout();
        checkout.setKeyID("rzp_test_ACak8WAWf9rje7");
        binding.locMap.setOnClickListener(v -> {
            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();
        });
        binding.imageView.setOnClickListener(v->{
            pickImageFromGallery();
        });
        binding.initButton.setOnClickListener(v -> {
            //TODO::form validation
            startPayment(finalAmount);
        });

        adapter = new ArrayAdapter<>((Context) this, R.layout.dropdown_item, durationList);
        binding.durationDropdown.setAdapter(adapter);

        binding.durationDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.price.setText("Price : " + planList.get(i).getPrice());
                finalAmount = planList.get(i).getPrice();
                duration = planList.get(i).getDuration();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void fetchDurationOptionsFromFirebase() {
        // Example: Assuming you have a 'durationOptions' collection in Firestore
        db.collection("StorePlan")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult()) {
                            planList.add(doc.toObject(Plan.class));
                            durationList.add(doc.get("duration") + " Days " + doc.get("name"));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
    private void writeTODb(String s, String imageUrl, int amount) {
        CollectionReference collectionReference = db.collection("Store");
        DocumentReference documentReference = collectionReference.document();

        String docId = documentReference.getId();
        Map<String, Object> data = new HashMap<>();
        data.put("StoreId", docId);
        data.put("OwnerId", Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        data.put("StoreUrl", imageUrl);
        data.put("address", Objects.requireNonNull(binding.address.getText()).toString());
        data.put("gstin", Objects.requireNonNull(binding.getin.getText()).toString());
        data.put("licenceNo", Objects.requireNonNull(binding.licNo.getText()).toString());
        data.put("StoreName", Objects.requireNonNull(binding.name.getText()).toString());
        data.put("visits", 0);
        data.put("rating", 0);
        data.put("StoreLat", 0);
        data.put("StoreLong", 0);
        data.put("verified", false);

        Timestamp currentTimestamp = Timestamp.now();
        Date fDate = new Date();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fDate = Date.from(Instant.now().plus(duration, ChronoUnit.DAYS));
        }
        Timestamp futureTimestamp = new Timestamp(fDate);
        Map<String, Object> data1 = new HashMap<>();
        data1.put("amount", amount);
        data1.put("orderId", s);
        data1.put("startDate", currentTimestamp);
        data1.put("endDate", futureTimestamp);
        documentReference.set(data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Store under Verification", Toast.LENGTH_SHORT).show();
                        db.collection("ShopOwners")
                                .document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .update("stores", FieldValue.arrayUnion(docId));
                        db.collection("ShopOwners")
                                .document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .update(data1);
                    } else {
                        Log.e("FirestoreExample", "Error adding document", task.getException());
                    }
                });
    }

    private void pickImageFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
    private void startPayment(int amount) {
        this.finalAmount = amount;
        checkout.setImage(R.drawable.findo_logo);
        JSONObject object = new JSONObject();
        try {
            object.put("name", "Findo Shop Plan Purchase");
            object.put("description", "Plan Purchase");
            object.put("theme.color", "");
            object.put("currency", "INR");
            object.put("amount", amount*100);
            object.put("prefill.email", Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());
            checkout.open(this, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            binding.imageView.setImageURI(selectedImageUri);
        }
    }
    public void uploadImage(String s) {
        StorageReference imageRef = storageReference.child("Store Images");
        imageRef.putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                    String imageUrl = downloadUri.toString();
                    writeTODb(s,imageUrl,finalAmount);
                }));
    }
    @Override
    public void onPaymentSuccess(String s) {
        try {
            uploadImage(s);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentError(int i, String s) {

    }

    private void setupSlideBar() {
        handler = new Handler(Objects.requireNonNull(Looper.myLooper()));
        imageList = new ArrayList<>();
        imageList.add("https://firebasestorage.googleapis.com/v0/b/findo-bdfd7.appspot.com/o/Store%20Promotion%2Fsponsership.png?alt=media&token=d64bf8fc-27cb-4da2-aa1e-a86af133ff27");
        imageList.add("https://firebasestorage.googleapis.com/v0/b/findo-bdfd7.appspot.com/o/Store%20Promotion%2FAdding%20product%20by%20scan.png?alt=media&token=e4c96b40-ef9a-408d-ad97-f44a7fb3a6b3");
        imageList.add("https://firebasestorage.googleapis.com/v0/b/findo-bdfd7.appspot.com/o/Store%20Promotion%2FNo%20QR%20.png?alt=media&token=7629bd5c-5989-4bdd-8201-101c370e9e73");
        imageList.add("https://firebasestorage.googleapis.com/v0/b/findo-bdfd7.appspot.com/o/Store%20Promotion%2Fregister%20your%20shops.png?alt=media&token=a332b936-e4bb-439a-bcec-29cff6c88ef2");


        sliderAdapter = new SliderAdapter(binding.imageSlider,this,imageList);

        binding.imageSlider.setAdapter(sliderAdapter);
        binding.imageSlider.setOffscreenPageLimit(3);
        binding.imageSlider.setClipToPadding(false);
        binding.imageSlider.setClipChildren(false);
        binding.imageSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        setSliderTransformer();
        CircleIndicator3 indicator = findViewById(R.id.indicator);
        indicator.setViewPager(binding.imageSlider);
        sliderAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
        binding.imageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable );
                if(position == imageList.size()-1){
                    handler.postDelayed(()->{
                        binding.imageSlider.post(() -> binding.imageSlider.setCurrentItem(0));
                    },3000);
                }else{
                    handler.postDelayed(runnable, 3000);
                }

            }
        });

    }
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            binding.imageSlider.setCurrentItem(binding.imageSlider.getCurrentItem() + 1);
        }
    };
    private void setSliderTransformer() {
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.65f + r*0.18f);
        });
        binding.imageSlider.setPageTransformer(transformer);
    }
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }
}