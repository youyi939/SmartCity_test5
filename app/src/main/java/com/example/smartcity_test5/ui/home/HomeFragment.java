package com.example.smartcity_test5.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcity_test5.MainActivity;
import com.example.smartcity_test5.R;
import com.example.smartcity_test5.ui.home.adapter.ServiceAdapter;
import com.example.smartcity_test5.ui.home.pojo.Img;
import com.example.smartcity_test5.ui.home.pojo.Item_service;
import com.example.smartcity_test5.util.KenUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Unbinder unbinder;
    @BindView(R.id.viewFlipper_home)
    ViewFlipper viewFlipper_home;
    private List<Img> imgList = new ArrayList<>();
    private List<Item_service> serviceList = new ArrayList<>();
    private List<Item_service> serviceList2 = new ArrayList<>();

    @BindView(R.id.recyclerService_home)
    RecyclerView recyclerService_home;
    @BindView(R.id.recyclerService_home2)
    RecyclerView recyclerService_home2;

    private ActionBar actionBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        actionBar =((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("智慧城市");
        unbinder = ButterKnife.bind(this,root);

        getLunbo();
        getService();

        return root;
    }

    public void getService() {
        serviceList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = KenUtil.Get("http://124.93.196.45:10002/service/service/list");
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int id = object.getInt("id");
                        String serviceName = object.getString("serviceName");
                        String url = "http://124.93.196.45:10002" + object.getString("imgUrl");
                        serviceList.add(new Item_service(id, serviceName, url));
                    }
                    serviceList.add(new Item_service(0, "更多服务", "R.drawable.ic_launcher_background"));
                    handler.sendEmptyMessage(2);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getLunbo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = KenUtil.Get("http://124.93.196.45:10002/userinfo/rotation/list?pageNum=1&pageSize=10&type=45");
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

    /**
     * 1:lunbo ok
     * 2:service ok
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    viewFlipper_home.removeAllViews();
                    for (int i = 0; i < imgList.size() ; i++) {
                        ImageView pp = new ImageView(getContext());
                        pp.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Glide.with(getContext()).load(imgList.get(i).getUrl()).into(pp);
                        viewFlipper_home.addView(pp);
                    }
                    break;
                case 2:
                    recyclerService_home.setLayoutManager(new GridLayoutManager(getActivity(),5));
                    recyclerService_home.setAdapter(new ServiceAdapter(serviceList,R.layout.item_service));
                    serviceList2 = serviceList.subList(0,4);
                    recyclerService_home2.setLayoutManager(new GridLayoutManager(getActivity(),2));
                    recyclerService_home2.setAdapter(new ServiceAdapter(serviceList2,R.layout.item_service));
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}