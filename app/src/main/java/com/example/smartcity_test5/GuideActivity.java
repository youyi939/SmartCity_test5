package com.example.smartcity_test5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartcity_test5.ui.home.pojo.Img;
import com.example.smartcity_test5.util.KenUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.btn2)
    ConstraintLayout btn2;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.btn)
    Button button;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private List<Img> imgList = new ArrayList<>();
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data",0);
        editor = getSharedPreferences("data",0).edit();
        getLunbo();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position){
                    case 0:
                        radioGroup.check(R.id.one);
                        break;
                    case 1:
                        radioGroup.check(R.id.two);
                        break;
                    case 2:
                        radioGroup.check(R.id.three);
                        break;
                    case 3:
                        radioGroup.check(R.id.four);
                        break;
                    case 4:
                        radioGroup.check(R.id.five);
                        break;
                }
                if (position + 1 == 5) {
                    button.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.VISIBLE);
                } else {
                    button.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void getLunbo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = KenUtil.Get("http://124.93.196.45:10002/userinfo/rotation/lists?pageNum=1&pageSize=10&type=47");
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String imgUrl = "http://124.93.196.45:10002" + object.getString("imgUrl");
                        String type = object.getString("type");
                        int id = object.getInt("id");
                        String sort = object.getString("sort");
                        imgList.add(new Img(id, imgUrl, type, sort));
                    }
                    handler.sendEmptyMessage(1);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @OnClick(R.id.btn)
    public void test(){
        if (sharedPreferences.getString("ip","k").equals("k")){
            Toast.makeText(GuideActivity.this,"请配置网络信息",Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(GuideActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.btn3)
    public void test2(){
        Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_guide,null);
        dialog.setContentView(view);
        EditText ip = view.findViewById(R.id.ip);
        EditText port = view.findViewById(R.id.port);
        Button btn4 = view.findViewById(R.id.button);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(ip.getText()) || TextUtils.isEmpty(port.getText())){
                    Toast.makeText(GuideActivity.this,"输入不得为空",Toast.LENGTH_SHORT).show();
                }else {
                    String ip1 = ip.getText().toString();
                    String port1 = port.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String json = KenUtil.Get("http://"+ip1+":"+port1);
                                JSONObject jsonObject = new JSONObject(json);
                                int code = jsonObject.getInt("code");
                                if (code == 401){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(GuideActivity.this,"端口校验成功",Toast.LENGTH_SHORT).show();
                                            editor.putString("ip",ip1);
                                            editor.putString("port",port1);
                                            editor.putBoolean("guide",false);
                                            editor.commit();
                                            dialog.dismiss();
                                        }
                                    });
                                }else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(GuideActivity.this, "端口校验失败", Toast.LENGTH_SHORT).show();
                                            ip.setText("");
                                            port.setText("");
                                        }
                                    });
                                }
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(GuideActivity.this, "端口校验失败", Toast.LENGTH_SHORT).show();
                                        ip.setText("");
                                        port.setText("");
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        });
        dialog.show();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    pagerAdapter = new PagerAdapter() {
                        @NonNull
                        @Override
                        public Object instantiateItem(@NonNull ViewGroup container, int position) {
                            ImageView imageView = new ImageView(GuideActivity.this);
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            Glide.with(GuideActivity.this).load(imgList.get(position).getUrl()).into(imageView);
                            container.addView(imageView);
                            return imageView;
                        }

                        @Override
                        public int getCount() {
                            return imgList.size();
                        }

                        @Override
                        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                            container.removeView((View) object);
                        }

                        @Override
                        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                            return view == object;
                        }
                    };

                    viewPager.setAdapter(pagerAdapter);
                    break;
            }
        }
    };



}