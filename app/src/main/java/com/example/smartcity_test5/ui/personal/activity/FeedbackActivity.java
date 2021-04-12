package com.example.smartcity_test5.ui.personal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartcity_test5.R;
import com.example.smartcity_test5.util.KenUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends AppCompatActivity {

    @BindView(R.id.feed)
    EditText feed;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data",0);
    }

    @OnClick(R.id.submit3)
    public void submit(){
        if (TextUtils.isEmpty(feed.getText())){
            Toast.makeText(FeedbackActivity.this,"输入部分为空",Toast.LENGTH_SHORT).show();
        }else {
            String context = feed.getText().toString();
            String url = "http://124.93.196.45:10002/userinfo/feedback";
            String token = sharedPreferences.getString("token","123");
            String userId = sharedPreferences.getString("userId","32213");
            send(url,token,userId,context);
        }
    }

    public void send(String url,String token ,String userId,String context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("content",context);
                    jsonObject.put("userId",userId);
                    String json = KenUtil.Post(url,token,jsonObject.toString());
                    JSONObject object = new JSONObject(json);
                    int code = object.getInt("code");
                    if (code ==200){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(FeedbackActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                feed.setText("");
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(FeedbackActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
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