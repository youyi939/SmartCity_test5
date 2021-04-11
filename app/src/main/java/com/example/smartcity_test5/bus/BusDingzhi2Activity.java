package com.example.smartcity_test5.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.smartcity_test5.BusInfoActivity;
import com.example.smartcity_test5.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusDingzhi2Activity extends AppCompatActivity {

    @BindView(R.id.data)
    TextView data;
    @BindView(R.id.time)
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_dingzhi2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.data_dialog)
    public void data_dialog(){
        DatePickerDialog pickerDialog = new DatePickerDialog(BusDingzhi2Activity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = String.format("%d 年 %d 月 %d 日 ", year, month + 1, dayOfMonth);
                        data.setText(date);
                    }
                }, 2000, 01, 01);
        pickerDialog.show();
    }
    @OnClick(R.id.time_dialog)
    public void time_dialog(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(BusDingzhi2Activity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String date = String.format("%d 时 %d 分 ", hourOfDay, minute);
                        time.setText(date);
                    }
                }, 00, 00, true);
        timePickerDialog.show();
    }

    @OnClick(R.id.last2)
    public void last(){
        finish();
    }
    @OnClick(R.id.next2)
    public void next(){
        if (time.getText().equals("") || data.getText().equals("")){
            Toast.makeText(BusDingzhi2Activity.this,"请设置日期时间",Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(BusDingzhi2Activity.this, BusInfoActivity.class);
            startActivity(intent);
        }

    }

}