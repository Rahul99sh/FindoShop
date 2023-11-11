package com.pro.findoshop.bottomSheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pro.findoshop.R;
import com.pro.findoshop.dataClasses.Items;
import com.pro.findoshop.databinding.ComparisionBottomSheetBinding;

public class Comparison extends BottomSheetDialogFragment {
    ComparisionBottomSheetBinding binding;
    private static Items item1,item2;
    private static final String ARG_ITEM_1 = "item_1";
    private static final String ARG_ITEM_2 = "item_2";
    public static Comparison newInstance(Items item1, Items item2) {
        Comparison fragment = new Comparison();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ITEM_1, item1);
        args.putParcelable(ARG_ITEM_2, item2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item1 = getArguments().getParcelable(ARG_ITEM_1);
            item2 = getArguments().getParcelable(ARG_ITEM_2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.comparision_bottom_sheet,container,false);
        binding.name1.setText(item1.getItemName());
        binding.name2.setText(item2.getItemName());
        binding.cate1.setText(item1.getCategory());
        binding.cate2.setText(item2.getCategory());
        Glide.with(requireContext()).load(item1.getItemUrl()).into(binding.img1);
        Glide.with(requireContext()).load(item2.getItemUrl()).into(binding.img2);
        binding.click2.setText(String.valueOf(item2.getClicks()));
        binding.click1.setText(String.valueOf(item1.getClicks()));
        binding.atc1.setText(String.valueOf(item1.getAddedToCart()));
        binding.atc2.setText(String.valueOf(item2.getAddedToCart()));
        binding.p1.setText(String.format("%.2f", (item1.getAddedToCart()/(double)item1.getClicks())*100)+"%");
        binding.p2.setText(String.format("%.2f", (item2.getAddedToCart()/(double)item2.getClicks())*100)+"%");
        return binding.getRoot();
    }
}