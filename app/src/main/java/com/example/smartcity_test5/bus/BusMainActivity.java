package com.example.smartcity_test5.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.example.smartcity_test5.R;
import com.example.smartcity_test5.bus.adapter.BusAdapter;
import com.example.smartcity_test5.bus.pojo.Bus;
import com.example.smartcity_test5.bus.pojo.Station;
import com.example.smartcity_test5.util.KenUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusMainActivity extends AppCompatActivity {

    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    private List<Bus> busList = new ArrayList<>();
    private BusAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_main);
        ButterKnife.bind(this);
        getBus();

    }


    public void getBus(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = KenUtil.Get("http://124.93.196.45:10002/userinfo/lines/list?pageNum=1&pageSize=10");
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray =jsonObject.getJSONArray("rows");
                    for (int i = 0; i < jsonArray.length() ; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String endTime = object.getString("endTime");
                        int id = object.getInt("id");
                        String name = object.getString("name");
                        String first = object.getString("first");
                        String end = object.getString("end");
                        String startTime = object.getString("startTime");
                        double price = object.getDouble("price");
                        String mileage = object.getString("mileage");
                        List<Station>stationList = new ArrayList<>();
                        String url = "http://124.93.196.45:10002/userinfo/busStop/list?pageNum=1&pageSize=10&linesId="+id;
                        String json1 = KenUtil.Get(url);
                        JSONObject jsonObject1 = new JSONObject(json1);
                        JSONArray jsonArray1 = jsonObject1.getJSONArray("rows");
                        for (int j = 0; j < jsonArray1.length() ; j++) {
                            JSONObject object1 = jsonArray1.getJSONObject(j);
                            String stepsId = object1.getString("stepsId");
                            String staname = object1.getString("name");
                            String sequence = object1.getString("sequence");
                            stationList.add(new Station(stepsId,staname,sequence));
                        }
                        busList.add(new Bus(endTime,id,name,first,end,startTime,price,mileage,stationList));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new BusAdapter(busList);
                                expandableListView.setAdapter(adapter);
                            }
                        });

                    }


                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



}