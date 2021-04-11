package com.example.smartcity_test5.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.smartcity_test5.R;
import com.example.smartcity_test5.bus.adapter.BusAdapter;
import com.example.smartcity_test5.util.KenUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusInfoActivity extends AppCompatActivity {

    @BindView(R.id.name_bus)
    EditText name;
    @BindView(R.id.phone_bus)
    EditText phone;
    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.spinner2)
    Spinner spinner2;

    private List<String> list = new ArrayList<>();

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data",0);

        for (int i = 0; i < BusAdapter.bus_s.getStationList().size() ; i++) {
            list.add(BusAdapter.bus_s.getStationList().get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(BusInfoActivity.this, R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(BusInfoActivity.this, R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
    }


    @OnClick(R.id.submit_bus)
    public void submit(){
        if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(phone.getText())){
            Toast.makeText(BusInfoActivity.this,"输入不得为空",Toast.LENGTH_SHORT).show();
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String token = sharedPreferences.getString("token","123");
                        int position = spinner1.getSelectedItemPosition();
                        int position2 = spinner2.getSelectedItemPosition();
                        String start  = list.get(position);
                        String end  = list.get(position);
                        String username = name.getText().toString();
                        String number = phone.getText().toString();
                        double price = BusAdapter.bus_s.getPrice();
                        String path = BusAdapter.bus_s.getName();
                        String status = "1";
                        String userId = sharedPreferences.getString("userId","1");
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("start",start);
                        jsonObject.put("end",end);
                        jsonObject.put("userName",username);
                        jsonObject.put("userTel",number);
                        jsonObject.put("price",String.valueOf(price));
                        jsonObject.put("path",path);
                        jsonObject.put("status",status);
                        jsonObject.put("userId",userId);

                        String json = KenUtil.Post("http://124.93.196.45:10002/userinfo/busOrders",token,jsonObject.toString());
                        JSONObject jsonObject1 = new JSONObject(json);
                        int code = jsonObject1.getInt("code");
                        if (code == 200){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(BusInfoActivity.this,"订单提交成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(BusInfoActivity.this,"订单提交失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }

    }

    @OnClick(R.id.last3)
    public void last(){
        finish();
    }

}