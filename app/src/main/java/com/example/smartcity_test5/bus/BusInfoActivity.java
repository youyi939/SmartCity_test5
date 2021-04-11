package com.example.smartcity_test5.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    public static String start;
    public static String end;
    public static String username;
    public static String number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);
        ButterKnife.bind(this);

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
            int position = spinner1.getSelectedItemPosition();
            int position2 = spinner2.getSelectedItemPosition();
             start  = list.get(position);
             end  = list.get(position2);
             username = name.getText().toString();
             number = phone.getText().toString();
            Intent intent = new Intent(BusInfoActivity.this,BusSubmitActivity.class);
            startActivity(intent);
        }

    }

    @OnClick(R.id.last3)
    public void last(){
        finish();
    }

}