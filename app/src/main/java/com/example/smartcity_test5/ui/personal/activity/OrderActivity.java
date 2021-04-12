package com.example.smartcity_test5.ui.personal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.example.smartcity_test5.R;
import com.example.smartcity_test5.ui.personal.adapter.OrderAdapter;
import com.example.smartcity_test5.ui.personal.pojo.Order;
import com.example.smartcity_test5.util.KenUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.listview_order)
    ListView listview_order;
    private SharedPreferences sharedPreferences;
    private List<Order> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data",0);
        String userId = sharedPreferences.getString("userId","32213");
        String token = sharedPreferences.getString("token","12");

        getOrder(userId,token);
    }

    public void getOrder(String userId,String token){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = KenUtil.Get_T("http://124.93.196.45:10002/userinfo/orders/list?userId="+userId,token);
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length() ; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String orderNum = object.getString("orderNum");
                        String createTime = object.getString("createTime");
                        orderList.add(new Order(orderNum,createTime));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            OrderAdapter adapter = new OrderAdapter(OrderActivity.this,R.layout.item_order,orderList);
                            listview_order.setAdapter(adapter);
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


}