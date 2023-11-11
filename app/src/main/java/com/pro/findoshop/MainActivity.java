package com.pro.findoshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;
import com.pro.findoshop.activities.ManageProduct;
import com.pro.findoshop.activities.StoreAnalytics;
import com.pro.findoshop.adapters.SliderAdapter;
import com.pro.findoshop.dataClasses.Items;
import com.pro.findoshop.dataClasses.Store;
import com.pro.findoshop.dataClasses.User;
import com.pro.findoshop.databinding.ActivityMainBinding;
import com.pro.findoshop.viewModels.StoreViewModel;
import com.pro.findoshop.viewModels.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Handler handler;
    private UserViewModel userViewModel;
    private StoreViewModel storeViewModel;
    SliderAdapter sliderAdapter;
    List<String> imageList;
    public static String storeId;
    public static User user;
    public static Store store;
    public static List<Items> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        setupSlideBar();
        userViewModel.getLiveUserData().observe(this, user -> {
            this.user = user;
            storeId = user.getStores().get(0);
            storeViewModel = new ViewModelProvider(this).get(StoreViewModel.class);
            storeViewModel.getLiveStoreData().observe(this, store -> {
                this.store = store;
                binding.shopName.setText(store.getStoreName());
                binding.address.setText(store.getAddress());
            });
            storeViewModel.getLiveStoreItemsData().observe(this, items -> {
                this.items = items;
            });
        });
        binding.manageProducts.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageProduct.class));
        });
        binding.analytics.setOnClickListener(v -> {
            startActivity(new Intent(this, StoreAnalytics.class));
        });
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

        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.collapsingbar.setTitle("Dashboard");
                    isShow = true;
                } else if(isShow) {
                    binding.collapsingbar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
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