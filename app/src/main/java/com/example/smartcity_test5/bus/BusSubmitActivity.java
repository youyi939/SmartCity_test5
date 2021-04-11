package com.example.smartcity_test5.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcity_test5.R;
import com.example.smartcity_test5.bus.adapter.BusAdapter;
import com.example.smartcity_test5.util.KenUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusSubmitActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;

    @BindView(R.id.name_submit)
    TextView name_submit;

    @BindView(R.id.phone_submit)
    TextView phone_submit;

    @BindView(R.id.start)
    TextView start;

    @BindView(R.id.end)
    TextView end;

    @BindView(R.id.time_submit)
    TextView time_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_submit);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data",0);

        name_submit.setText(BusInfoActivity.username);
        phone_submit.setText(BusInfoActivity.number);
        time_submit.setText(BusDingzhi2Activity.data_s+"\n"+BusDingzhi2Activity.time_s);
        start.setText(BusInfoActivity.start);
        end.setText(BusInfoActivity.end);
    }



    @OnClick(R.id.submit2)
    public void submit(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String token = sharedPreferences.getString("token","123");
                    String start = BusInfoActivity.start;
                    String end = BusInfoActivity.end;
                    String username = BusInfoActivity.username;
                    String number = BusInfoActivity.number;
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
                                Toast.makeText(BusSubmitActivity.this,"订单提交成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BusSubmitActivity.this,"订单提交失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @OnClick(R.id.last4)
    public void last(){
        finish();
    }

}