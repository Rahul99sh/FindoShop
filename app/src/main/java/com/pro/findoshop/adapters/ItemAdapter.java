package com.pro.findoshop.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.pro.findoshop.R;
import com.pro.findoshop.activities.EditProducts;
import com.pro.findoshop.dataClasses.Items;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    Context context;
    List<Items> items;

    public ItemAdapter(Context context, List<Items> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.name.setText(items.get(position).getItemName());
        holder.desc.setText(items.get(position).getItemDescription());
        holder.price.setText(String.valueOf(items.get(position).getPrice()));
        Glide.with(context).load(items.get(position).getItemUrl()).into(holder.image);
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, EditProducts.class);
            i.putExtra("item", items.get(position));
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView name,desc,price;
        ImageView image;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            desc = itemView.findViewById(R.id.item_short_desc);
            price = itemView.findViewById(R.id.item_price);
            image = itemView.findViewById(R.id.item_img);
        }
    }

}
