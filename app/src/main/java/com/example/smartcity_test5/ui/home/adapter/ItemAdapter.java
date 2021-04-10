package com.example.smartcity_test5.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcity_test5.R;
import com.example.smartcity_test5.ui.home.pojo.ItemData;
import com.example.smartcity_test5.ui.home.pojo.Item_service;

import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int resourceId;
    private RecyclerView.ViewHolder holder;
    private List<ItemData> list;

    public ItemAdapter(List<ItemData> list, int resourceId) {
        this.list = list;
        this.resourceId = resourceId;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId,null);              //设置为null时item为居中显示
        holder = new RecyclerView.ViewHolder(view) {};
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemData itemData = list.get(position);
        TextView title = holder.itemView.findViewById(R.id.title_data);
        TextView content = holder.itemView.findViewById(R.id.content_data);
        TextView time = holder.itemView.findViewById(R.id.createTime_data);
        TextView num = holder.itemView.findViewById(R.id.viewNum_data);
        ImageView imageView = holder.itemView.findViewById(R.id.img_data);

        title.setText(itemData.getTitle());
        content.setText(itemData.getContent());
        time.setText(itemData.getCreateTime());
        num.setText(String.valueOf(itemData.getViewNumber()));

        Glide.with(holder.itemView.getContext()).load(itemData.getUrl()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

