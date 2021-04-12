package com.example.smartcity_test5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartcity_test5.util.KenUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username_login)
    EditText username_login;
    @BindView(R.id.password_login)
    EditText password_login;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data",0);
        editor = getSharedPreferences("data",0).edit();
    }


    @OnClick(R.id.login)
    public void login(View view){
        if (TextUtils.isEmpty(username_login.getText()) || TextUtils.isEmpty(password_login.getText())){
            Toast.makeText(LoginActivity.this,"输入不得为空",Toast.LENGTH_SHORT).show();
        }else {
            getToken();
        }
    }

    public void getToken(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String port = sharedPreferences.getString("port","10002");
                String ip = sharedPreferences.getString("ip","124.93.196.45");
                String username = username_login.getText().toString();
                String password = password_login.getText().toString();
                try {
                    JSONObject object = new JSONObject();
                    object.put("username",username);
                    object.put("password",password);
                    String json = KenUtil.Post("http://"+ip+":"+port+"/login","",object.toString());
                    JSONObject jsonObject = new JSONObject(json);
                    int code = jsonObject.getInt("code");
                    if (code == 200){
                        String token = jsonObject.getString("token");
                        editor.putString("token",token);
                        editor.putString("username",username);
                        editor.putString("password",password);
                        editor.commit();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
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