package com.example.smartcity_test5.traffic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.smartcity_test5.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrafficListActivity extends AppCompatActivity {
    @BindView(R.id.layout_traffic)
    LinearLayout layout;
    @BindView(R.id.more_traffic)
    Button more_traffic;
    @BindView(R.id.listView_traffic)
    ListView listView_traffic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_list);
        ButterKnife.bind(this);
        Log.i("Ken", "onCreate: "+TrafficActivity.number+" "+TrafficActivity.number2);
    }
}