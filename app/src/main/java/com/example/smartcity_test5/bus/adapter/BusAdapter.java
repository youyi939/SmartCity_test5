package com.example.smartcity_test5.bus.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.smartcity_test5.R;
import com.example.smartcity_test5.bus.BusDingzhiActivity;
import com.example.smartcity_test5.bus.pojo.Bus;
import com.example.smartcity_test5.bus.pojo.Station;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusAdapter extends BaseExpandableListAdapter {
    public static String s_e;
    public static Bus bus_s;

    private List<Bus> busList;

    @BindView(R.id.lineName)
    TextView lineName;
    @BindView(R.id.start_end)
    TextView start_end;
    @BindView(R.id.price_bus)
    TextView price_bus;

    public BusAdapter(List<Bus> busList) {
        this.busList = busList;
    }

    @Override
    public int getGroupCount() {
        return busList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return busList.get(i).getStationList().size();
    }

    @Override
    public Object getGroup(int i) {
        return busList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return busList.get(i).getStationList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null){
            view =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bus,null);
        }
        ButterKnife.bind(this,view);
        Bus bus = busList.get(i);
        lineName.setText("线路名："+bus.getName());
        start_end.setText(busList.get(i).getStationList().get(0).getName()+"--->"+busList.get(i).getStationList().get(busList.get(i).getStationList().size()-1).getName());
        price_bus.setText(busList.get(i).getPrice()+"元");

        lineName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusAdapter.s_e = "起终点："+busList.get(i).getStationList().get(0).getName()+"--->"+busList.get(i).getStationList().get(busList.get(i).getStationList().size()-1).getName();
                BusAdapter.bus_s = bus;
                Intent intent = new Intent(viewGroup.getContext(), BusDingzhiActivity.class);
                viewGroup.getContext().startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        Station station = busList.get(i).getStationList().get(i1);
        if (view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_buschaild,null);
        }
        TextView name = view.findViewById(R.id.name);
        name.setText(station.getName());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
