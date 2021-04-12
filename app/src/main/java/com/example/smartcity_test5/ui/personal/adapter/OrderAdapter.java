package com.example.smartcity_test5.ui.personal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smartcity_test5.R;
import com.example.smartcity_test5.ui.personal.pojo.Order;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {
    private int resourceId;

    public OrderAdapter(@NonNull Context context, int resource, @NonNull List<Order> objects) {
        super(context, resource, objects);

        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }
        Order order = getItem(position);
        TextView num = convertView.findViewById(R.id.num_order);
        TextView time = convertView.findViewById(R.id.time_order);
        num.setText("订单号："+order.getOrderNum());
        time.setText("创建时间: "+ order.getCreateTime());


        return convertView;
    }
}
