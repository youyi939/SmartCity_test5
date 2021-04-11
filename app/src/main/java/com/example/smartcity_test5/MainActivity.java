package com.example.smartcity_test5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.smartcity_test5.util.KenUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.PrimitiveIterator;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("data",0);
        editor = getSharedPreferences("data",0).edit();
        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        if (sharedPreferences.getString("ip","k").equals("k")){
            Intent intent = new Intent(MainActivity.this,GuideActivity.class);
            startActivity(intent);
        }
        getToken();

    }

    public void getToken(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String port = sharedPreferences.getString("port","10002");
                String ip = sharedPreferences.getString("ip","124.93.196.45");
                try {
                    JSONObject object = new JSONObject();
                    object.put("username","KenChen");
                    object.put("password","123");
                    String json = KenUtil.Post("http://"+ip+":"+port+"/login","",object.toString());
                    JSONObject jsonObject = new JSONObject(json);
                    int code = jsonObject.getInt("code");
                    if (code == 200){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                        String token = jsonObject.getString("token");
                        editor.putString("token",token);
                        editor.commit();
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
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