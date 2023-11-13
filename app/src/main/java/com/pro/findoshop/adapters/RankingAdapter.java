package com.pro.findoshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pro.findoshop.R;
import com.pro.findoshop.dataClasses.Store;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder>{

    List<Store> storeList;
    Context context;
    String id;

    public RankingAdapter(List<Store> storeList, Context context, String id) {
        this.storeList = storeList;
        this.context = context;
        this.id = id;
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ranking_item,parent,false);
        return new RankingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {
//        if(storeList.get(position).getStoreId().equals(id)) holder.
        holder.rank.setText(String.valueOf(position+1));
        holder.name.setText(storeList.get(position).getStoreName());
        holder.visits.setText(String.valueOf(storeList.get(position).getVisits()));
        holder.rating.setText(String.valueOf(storeList.get(position).getRating()));
        Glide.with(context).load(storeList.get(position).getStoreUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public static class RankingViewHolder extends RecyclerView.ViewHolder{

        TextView rank,rating,visits,name;
        CircleImageView image;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            rating = itemView.findViewById(R.id.rating);
            visits = itemView.findViewById(R.id.visits);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.store_img);
        }
    }
}
