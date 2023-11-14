package com.pro.findoshop.bottomSheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pro.findoshop.R;
import com.pro.findoshop.dataClasses.Items;
import com.pro.findoshop.databinding.ComparisionBottomSheetBinding;
import com.pro.findoshop.databinding.EditItemBottomsheetBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditItemBottomSheet extends BottomSheetDialogFragment {
    EditItemBottomsheetBinding binding;
    private Items item;
    private String storeId;
    private static final String ARG_ITEM_1 = "item";
    private static final String ARG_ITEM_2 = "store_id";

    FirebaseFirestore db;
    public static EditItemBottomSheet newInstance(Items item1, String storeId) {
        EditItemBottomSheet fragment = new EditItemBottomSheet();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ITEM_1, item1);
        args.putString(ARG_ITEM_2, storeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = getArguments().getParcelable(ARG_ITEM_1);
            db = FirebaseFirestore.getInstance();
            storeId = getArguments().getString(ARG_ITEM_2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.edit_item_bottomsheet,container,false);
        binding.name.setText(item.getItemName());
        binding.category.setText(item.getCategory());
        binding.price.setText(item.getPrice());
        binding.desc.setText(item.getItemDescription());
        binding.update.setOnClickListener(v -> {
            updateDetails();
        });
        return binding.getRoot();
    }

    private void updateDetails() {
        Map<String,Object> mp = new HashMap<>();
        mp.put("ItemName", Objects.requireNonNull(binding.name.getText()).toString());
        mp.put("ItemDescription", Objects.requireNonNull(binding.desc.getText()).toString());
        mp.put("Category", Objects.requireNonNull(binding.category.getText()).toString());
        mp.put("price", Objects.requireNonNull(binding.price.getText()).toString());
        db.collection("Store").document(storeId).collection("Items")
                .document(item.getItemId()).update(mp).addOnCompleteListener(task -> {
                    Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                    requireActivity().finish();
                    dismiss();
                }).addOnFailureListener(e -> {

                });
    }
}