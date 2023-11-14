package com.pro.findoshop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import com.pro.findoshop.R;
import com.pro.findoshop.adapters.ItemAdapter;
import com.pro.findoshop.dataClasses.Store;
import com.pro.findoshop.databinding.ActivityManageProductBinding;
import com.pro.findoshop.viewModels.StoreViewModel;

public class ManageProduct extends AppCompatActivity {

    ActivityManageProductBinding binding;
    private StoreViewModel storeViewModel;
    ItemAdapter adapter;
    Store store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_product);
        storeViewModel = new ViewModelProvider(this).get(StoreViewModel.class);
        storeViewModel.getLiveStoreData().observe(this, store -> {
            this.store = store;
            binding.shopName.setText(store.getStoreName());
            binding.address.setText(store.getAddress());
        });
        storeViewModel.getLiveStoreItemsData().observe(this, items -> {
            adapter = new ItemAdapter(this,items,store);
            binding.itemRv.setAdapter(adapter);
            binding.itemCount.setText(String.valueOf(items.size())+ " items Listed");
        });

    }

}