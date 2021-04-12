package com.example.smartcity_test5.ui.personal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class ChangeActivity extends AppCompatActivity {

    @BindView(R.id.oldpass)
    EditText oldpass;
    @BindView(R.id.pass2)
    EditText newpass2;
    @BindView(R.id.newpass)
    EditText newpass;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data",0);
        editor = getSharedPreferences("data",0).edit();
    }

    @OnClick(R.id.changepass)
    public void change() {
        if (TextUtils.isEmpty(oldpass.getText()) || TextUtils.isEmpty(newpass2.getText()) || TextUtils.isEmpty(newpass.getText())) {
            Toast.makeText(ChangeActivity.this, "输入不得为空", Toast.LENGTH_SHORT).show();
        } else {
            String old = oldpass.getText().toString();
            String pass = newpass.getText().toString();
            String pass2 = newpass2.getText().toString();
            String password = sharedPreferences.getString("password","123");
            String token = sharedPreferences.getString("token","123");
            String userId = sharedPreferences.getString("userId","32213");

            if (!pass.equals(pass2)) {
                Toast.makeText(ChangeActivity.this, "输入密码不一致", Toast.LENGTH_SHORT).show();
            }else if (!old.equals(password)){
                Toast.makeText(ChangeActivity.this, "原密码不正确", Toast.LENGTH_SHORT).show();
            }else {
                sendMsg(old,pass,token,userId);
            }

        }
    }


    public void sendMsg(String old,String password,String token,String userId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userId",userId);
                    jsonObject.put("oldPwd",old);
                    jsonObject.put("password",password);
                    String json = KenUtil.Put_T("http://124.93.196.45:10002/system/user/resetPwd",token,jsonObject.toString());
                    JSONObject object = new JSONObject(json);
                    int code = object.getInt("code");
                    if (code ==200){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChangeActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                editor.putString("password",password);
                                editor.commit();
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChangeActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
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