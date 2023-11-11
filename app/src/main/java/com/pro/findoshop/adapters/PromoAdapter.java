package com.pro.findoshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.Timestamp;
import com.pro.findoshop.R;
import com.pro.findoshop.dataClasses.Promotion;

import java.time.Duration;
import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.PromoViewHolder> {

    List<Promotion> promotionList;
    Context context;

    public PromoAdapter(List<Promotion> promotionList, Context context) {
        this.promotionList = promotionList;
        this.context = context;
    }

    @NonNull
    @Override
    public PromoAdapter.PromoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.promo_item,parent,false);
        return new PromoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoAdapter.PromoViewHolder holder, int position) {
        Glide.with(context).load(promotionList.get(position).getImageUrl()).placeholder(R.drawable.findologo1).into(holder.image);
        Duration duration = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            duration = Duration.between(promotionList.get(position).getEndDate().toDate().toInstant(), Timestamp.now().toDate().toInstant());
            holder.days.setText(String.valueOf(Math.abs(duration.toDays())) + " Days Left!");
        }

    }

    @Override
    public int getItemCount() {
        return promotionList.size();
    }

    public static class PromoViewHolder extends RecyclerView.ViewHolder{
        ImageView image,edit,delete;
        TextView days;

        public PromoViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.promo_img);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delt);
            days = itemView.findViewById(R.id.time_left);
        }
    }
}
