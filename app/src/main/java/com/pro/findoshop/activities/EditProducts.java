package com.pro.findoshop.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.pro.findoshop.R;
import com.pro.findoshop.adapters.SliderAdapter;
import com.pro.findoshop.adapters.SliderProductAdapter;
import com.pro.findoshop.dataClasses.Items;
import com.pro.findoshop.databinding.ActivityEditProductsBinding;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class EditProducts extends AppCompatActivity {

    Items item;
    ActivityEditProductsBinding binding;
    SliderProductAdapter sliderAdapter;
    List<String> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_products);
        item = (Items) getIntent().getSerializableExtra("item");
        assert item != null;
        binding.price.setText(item.getPrice());
        binding.itemNameTxt.setText(item.getItemName());
        binding.itemDesc.setText(item.getItemDescription());
        imageList = new ArrayList<>();
        imageList.add(item.getItemUrl());
        imageList.add(item.getItemUrl());
        setupSlideBar();
    }

    private void setupSlideBar() {

        sliderAdapter = new SliderProductAdapter(binding.imageSlider,this,imageList);

        binding.imageSlider.setAdapter(sliderAdapter);
        binding.imageSlider.setOffscreenPageLimit(3);
        binding.imageSlider.setClipToPadding(false);
        binding.imageSlider.setClipChildren(false);
        binding.imageSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        setSliderTransformer();
        CircleIndicator3 indicator = findViewById(R.id.indicator);
        indicator.setViewPager(binding.imageSlider);
        sliderAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());

    }
    private void setSliderTransformer() {
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.65f + r*0.18f);
        });
        binding.imageSlider.setPageTransformer(transformer);
    }
}