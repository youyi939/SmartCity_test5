package com.example.smartcity_test5.traffic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.smartcity_test5.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrafficActivity extends AppCompatActivity {

    @BindView(R.id.spinner_traffic)
    Spinner spinner;
    @BindView(R.id.number_traffic)
    EditText number_traffic;            //车牌号
    @BindView(R.id.engineNumber)
    EditText engineNumber;              //发动机号

    private List<String> strings = new ArrayList<>();

    public static String number;
    public static String number2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
        ButterKnife.bind(this);
        strings.add("京");
        strings.add("黑");
        strings.add("辽");
        strings.add("琼");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TrafficActivity.this,R.layout.support_simple_spinner_dropdown_item,strings);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

    }

    @OnClick(R.id.query)
    public void query(){
        if (TextUtils.isEmpty(number_traffic.getText())  || TextUtils.isEmpty(engineNumber.getText())){
            Toast.makeText(TrafficActivity.this,"输入不得为空",Toast.LENGTH_SHORT).show();
        }else {
            int position = spinner.getSelectedItemPosition();
            number =strings.get(position) + number_traffic.getText().toString();
            number2 = engineNumber.getText().toString();
            Intent intent = new Intent(TrafficActivity.this,TrafficListActivity.class);
            startActivity(intent);
        }
    }

}