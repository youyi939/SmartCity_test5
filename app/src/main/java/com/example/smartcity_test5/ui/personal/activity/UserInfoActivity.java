package com.example.smartcity_test5.ui.personal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartcity_test5.R;
import com.example.smartcity_test5.util.KenUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @BindView(R.id.img_userinfo)
    ImageView imageView;
    @BindView(R.id.nike_userinfo)
    EditText nike;
    @BindView(R.id.phone_user)
    EditText phone_user;
    @BindView(R.id.email_user)
    EditText email_user;
    @BindView(R.id.idCard_user)
    TextView idCard_user;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data",0);
        editor = getSharedPreferences("data",0).edit();

        getUserInfo();
    }

    public void getUserInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String token = sharedPreferences.getString("token","k");
                    String json = KenUtil.Get_T("http://124.93.196.45:10002/getInfo",token);
                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject object = jsonObject.getJSONObject("user");
                    String avatar ="http://124.93.196.45:10002"+ object.getString("avatar");
                    String nickName = object.getString("nickName");
                    String number = object.getString("phonenumber");
                    String email = object.getString("email");
                    String idCard = object.getString("idCard");
                    int sex = object.getInt("sex");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(UserInfoActivity.this).load(avatar).into(imageView);
                            nike.setText(nickName);
                            phone_user.setText(number);
                            email_user.setText(email);
                            int length = idCard.length();
                            String msg1 = idCard.substring(0,2);
                            String msg2 = idCard.substring(length-4,length);
                            idCard_user.setText(msg1+"***************"+msg2);
                            if (sex == 1){
                                radioGroup.check(R.id.mail);
                            }else if (sex == 0){
                                radioGroup.check(R.id.fmail);
                            }
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    @OnClick(R.id.change)
    public void change(){
        if (TextUtils.isEmpty(phone_user.getText()) || TextUtils.isEmpty(nike.getText())){
            Toast.makeText(UserInfoActivity.this,"输入不得为空",Toast.LENGTH_SHORT).show();
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String phone = phone_user.getText().toString();
                        String email = email_user.getText().toString();
                        String nikename = nike.getText().toString();
                        String token = sharedPreferences.getString("token","k");
                        int sex = 1;
                        if (radioGroup.getCheckedRadioButtonId()==R.id.mail){
                            sex=1;
                        }else {
                            sex = 0;
                        }
                        String url = "http://124.93.196.45:10002/system/user/updata?userId=32213&nickName="+nikename+"&email="+email+"&phonenumber="+phone+"&sex="+sex;
                        String json = KenUtil.Post(url,token,"");
                        JSONObject jsonObject = new JSONObject(json);
                        int code = jsonObject.getInt("code");
                        if (code == 200){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(UserInfoActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
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



}