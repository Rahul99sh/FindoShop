package com.pro.findoshop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.pro.findoshop.R;
import com.pro.findoshop.viewModels.MaintainanceViewModel;

public class Maintainance extends AppCompatActivity {
    MaintainanceViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintainance);
        TextView desc = findViewById(R.id.desc);
        viewModel = new ViewModelProvider(this).get(MaintainanceViewModel.class);
        viewModel.loadData();
        viewModel.getData().observe(this, data -> {
            desc.setText(data.getDesc());
            if(!data.isMaintainance_shop()){
                Intent i =  new Intent(this, FirstScreen.class);
                startActivity(i);
                finish();
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.stopListeningData();
    }

}