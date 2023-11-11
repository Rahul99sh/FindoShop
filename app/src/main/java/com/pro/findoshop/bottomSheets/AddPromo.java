package com.pro.findoshop.bottomSheets;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
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
import com.pro.findoshop.dataClasses.Items;
import com.pro.findoshop.dataClasses.PromoMeta;
import com.pro.findoshop.dataClasses.Store;
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

public class AddPromo extends BottomSheetDialogFragment {

    private ImageView imageView;
    private AppCompatSpinner durationDropdown;
    private AppCompatButton initiatePaymentButton;

    private List<String> durationOptions;
    private ArrayAdapter<String> adapter;
    List<PromoMeta> promoMetaList;
    private FirebaseFirestore db;

    Uri selectedImageUri;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    Checkout checkout;
    FirebaseAuth mAuth;
    int finalAmount;
    Store store;
    private static final int PICK_IMAGE_REQUEST = 1;

    public static AddPromo newInstance() {
        return new AddPromo();
    }

    private static final String ARG_STORE = "store";
    public static AddPromo newInstance(Store store) {
        AddPromo fragment = new AddPromo();
        Bundle args = new Bundle();
        args.putParcelable(ARG_STORE, store);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            store = getArguments().getParcelable(ARG_STORE);
        }
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        durationOptions = new ArrayList<>();
        promoMetaList = new ArrayList<>();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("Promotions");
        Checkout.preload(requireContext().getApplicationContext());
        checkout = new Checkout();
        checkout.setKeyID("rzp_test_ACak8WAWf9rje7");
        fetchDurationOptionsFromFirebase();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_promo_bottom_sheet, container, false);

        imageView = view.findViewById(R.id.imageView);
        durationDropdown = view.findViewById(R.id.durationDropdown);
        initiatePaymentButton = view.findViewById(R.id.initButton);
        TextView priceText = view.findViewById(R.id.price);
        // Set up the adapter for the dropdown
        adapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, durationOptions);
        durationDropdown.setAdapter(adapter);
        imageView.setOnClickListener(v -> {
            pickImageFromGallery();
        });
        durationDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priceText.setText("Price : " + promoMetaList.get(i).getPrice());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // Set up click listener for the initiate payment button
        initiatePaymentButton.setOnClickListener(v -> {
            String selectedDuration = (String) durationDropdown.getSelectedItem().toString();
            if (!selectedDuration.equals(" ") && selectedImageUri != null) {
                int duration = Integer.parseInt(selectedDuration.substring(0,2));
                int price = -1;
                for(PromoMeta promoMeta : promoMetaList){
                    if(promoMeta.getDuration() == duration) price = promoMeta.getPrice();
                }
                startPayment(price);
            } else if (selectedImageUri == null) {
                Toast.makeText(requireContext(), "Please select a promo image", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Please select promotion duration", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    // Fetch duration options from Firebase
    private void fetchDurationOptionsFromFirebase() {
        // Example: Assuming you have a 'durationOptions' collection in Firestore
        db.collection("PromotionMeta")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult()) {
                            promoMetaList.add(doc.toObject(PromoMeta.class));
                            durationOptions.add(doc.get("duration") + " Days");
                        }
                        adapter.notifyDataSetChanged();
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
            object.put("name", "Findo Shop");
            object.put("description", "Promotion 30 Days Subscription");
            object.put("theme.color", "");
            object.put("currency", "INR");
            object.put("amount", amount*100);
            object.put("prefill.email", Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());
            checkout.open(requireActivity(), object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
        }
    }
    public void uploadImage(String s) {
        StorageReference imageRef = storageReference.child(Objects.requireNonNull(selectedImageUri.getLastPathSegment()));
        imageRef.putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                    String imageUrl = downloadUri.toString();
                    // Display the download URL in a Toast
                    Toast.makeText(requireContext(), "Image uploaded!\n" + imageUrl, Toast.LENGTH_LONG).show();
                    writeTODb(s,imageUrl,finalAmount);
                }));
    }

    private void writeTODb(String s, String imageUrl, int amount) {
        CollectionReference collectionReference = db.collection("Promotion");
        DocumentReference documentReference = collectionReference.document();
        Timestamp currentTimestamp = Timestamp.now();
        Date fDate = new Date();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fDate = Date.from(Instant.now().plus(30, ChronoUnit.DAYS));
        }
        String docId = documentReference.getId();
        Timestamp futureTimestamp = new Timestamp(fDate);
        Map<String, Object> data = new HashMap<>();
        data.put("promoId", docId);
        data.put("imageUrl", imageUrl);
        data.put("orderId", s);
        data.put("amount", amount);
        data.put("storeId", store.getStoreId());
        data.put("paymentStatus", true);
        data.put("startDate", currentTimestamp);
        data.put("endDate", futureTimestamp);
        documentReference.set(data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(requireContext(), "Promotion Created", Toast.LENGTH_SHORT).show();
                        db.collection("Store")
                                .document(store.getStoreId())
                                .update("promoIds", FieldValue.arrayUnion(docId));
                        dismiss();
                    } else {
                        Log.e("FirestoreExample", "Error adding document", task.getException());
                    }
                });
    }
}