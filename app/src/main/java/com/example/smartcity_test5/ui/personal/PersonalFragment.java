package com.example.smartcity_test5.ui.personal;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartcity_test5.LoginActivity;
import com.example.smartcity_test5.MainActivity;
import com.example.smartcity_test5.R;
import com.example.smartcity_test5.ui.personal.activity.ChangeActivity;
import com.example.smartcity_test5.ui.personal.activity.FeedbackActivity;
import com.example.smartcity_test5.ui.personal.activity.OrderActivity;
import com.example.smartcity_test5.ui.personal.activity.UserInfoActivity;
import com.example.smartcity_test5.util.KenUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PersonalFragment extends Fragment {

    private PersonalViewModel mViewModel;

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    private ActionBar actionBar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Unbinder unbinder;

    @BindView(R.id.img_personal)
    ImageView imageView;
    @BindView(R.id.nike_personal)
    TextView nike;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.personal_fragment, container, false);
        actionBar =((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("个人中心");
        unbinder = ButterKnife.bind(this,root);
        sharedPreferences = getActivity().getSharedPreferences("data",0);
        editor = getActivity().getSharedPreferences("data",0).edit();
        getToken();
        getUserInfo();
        return root;
    }

    @OnClick({R.id.userInfo,R.id.change,R.id.order,R.id.feedback})
    public void test(View view){
        switch (view.getId()){
            case R.id.userInfo:
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.change:
                Intent intent1 = new Intent(getContext(), ChangeActivity.class);
                getActivity().startActivity(intent1);
                break;
            case R.id.order:
                Intent intent3 = new Intent(getContext(), OrderActivity.class);
                getActivity().startActivity(intent3);
                break;
            case R.id.feedback:
                Intent intent2 = new Intent(getContext(), FeedbackActivity.class);
                getActivity().startActivity(intent2);
                break;
        }
    }

    public void getToken(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String port = sharedPreferences.getString("port","10002");
                String ip = sharedPreferences.getString("ip","124.93.196.45");
                String username = sharedPreferences.getString("username","kenchen");
                String password = sharedPreferences.getString("password","123");
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
                        editor.commit();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getContext()).load(avatar).into(imageView);
                            nike.setText(nickName);
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    @OnClick(R.id.logout)
    public void logout() {
        editor.clear().commit();
        editor.putBoolean("guide",false);
        editor.commit();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        getActivity().startActivity(intent);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PersonalViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}