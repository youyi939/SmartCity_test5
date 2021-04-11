package com.example.smartcity_test5.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartcity_test5.R;
import com.example.smartcity_test5.bus.adapter.BusAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusDingzhiActivity extends AppCompatActivity {


    @BindView(R.id.se)
    TextView textView;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.km)
    TextView km;
    @BindView(R.id.load)
    TextView load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_dingzhi);
        ButterKnife.bind(this);
        textView.setText(BusAdapter.s_e);
        price.setText("票价:"+BusAdapter.bus_s.getPrice()+"元");
        km.setText("里程："+BusAdapter.bus_s.getMileage());

        StringBuffer luxian = new StringBuffer();
        for (int i = 0; i < BusAdapter.bus_s.getStationList().size() ; i++) {
            luxian.append("·"+BusAdapter.bus_s.getStationList().get(i).getName()+"\n");
        }

        load.setText(luxian);

    }

    @OnClick(R.id.next)
    public void next(){
        Intent intent = new Intent(BusDingzhiActivity.this,BusDingzhi2Activity.class);
        startActivity(intent);
    }


    @OnClick(R.id.last)
    public void last(){
        finish();
    }

}