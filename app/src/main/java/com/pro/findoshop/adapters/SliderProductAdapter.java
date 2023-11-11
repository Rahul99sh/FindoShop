package com.pro.findoshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.pro.findoshop.R;

import java.util.List;

public class SliderProductAdapter extends RecyclerView.Adapter<SliderProductAdapter.SliderViewHolder> {

    ViewPager2 viewPager2;
    Context context;
    List<String> images;

    public SliderProductAdapter(ViewPager2 viewPager2, Context context, List<String> images) {
        this.viewPager2 = viewPager2;
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.img_slider_item,parent,false);
        return new SliderViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        Glide.with(context).load(images.get(position)).into(holder.image);
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_auto_image_slider);
        }
    }
}
