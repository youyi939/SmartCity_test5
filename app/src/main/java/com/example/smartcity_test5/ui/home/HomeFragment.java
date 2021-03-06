package com.example.smartcity_test5.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.example.smartcity_test5.ui.home.adapter.ItemAdapter;
import com.example.smartcity_test5.ui.home.adapter.ServiceAdapter;
import com.example.smartcity_test5.ui.home.pojo.Img;
import com.example.smartcity_test5.ui.home.pojo.Item;
import com.example.smartcity_test5.ui.home.pojo.ItemData;
import com.example.smartcity_test5.ui.home.pojo.Item_service;
import com.example.smartcity_test5.util.KenUtil;
import com.google.android.material.tabs.TabLayout;

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
    private List<Item> itemList = new ArrayList<>();

    @BindView(R.id.recyclerService_home)
    RecyclerView recyclerService_home;
    @BindView(R.id.recyclerService_home2)
    RecyclerView recyclerService_home2;
    @BindView(R.id.tab_home)
    TabLayout tab_home;
    @BindView(R.id.listXinwen_home)
    RecyclerView listXinwen_home;

    Thread thread;
    Thread thread1;
    Thread thread2;

    private ActionBar actionBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        actionBar =((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("????????????");
        unbinder = ButterKnife.bind(this,root);

        getLunbo();
        getService();
        getItem();

        tab_home.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Message message = new Message();
                message.what = 4;
                message.obj = tab.getPosition();
                handler.sendMessage(message);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }

    public void getService() {
        serviceList.clear();
        thread1 =  new Thread(new Runnable() {
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
                    serviceList.add(new Item_service(0, "????????????", "R.drawable.ic_launcher_background"));
                    handler.sendEmptyMessage(2);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
    }

    public void getLunbo() {
        thread2 =  new Thread(new Runnable() {
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
        });
        thread2.start();
    }

    public void getItem() {
        thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = KenUtil.Get("http://124.93.196.45:10002/system/dict/data/type/press_category");
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int dictCode = object.getInt("dictCode");
                        String dictLabel = object.getString("dictLabel");
                        List<ItemData> dataList = new ArrayList<>();

                        String url = "http://124.93.196.45:10002/press/press/list?pageNum=1&pageSize=10&pressCategory=" + dictCode;
                        String json1 = KenUtil.Get(url);
                        JSONObject jsonObject1 = new JSONObject(json1);
                        JSONArray jsonArray1 = jsonObject1.getJSONArray("rows");
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            JSONObject object1 = jsonArray1.getJSONObject(j);
                            String title = object1.getString("title");
                            String content = object1.getString("content");
                            String imgUrl = "http://124.93.196.45:10002" + object1.getString("imgUrl");
                            int viewsNumber = object1.getInt("viewsNumber");
                            String createTime = object1.getString("createTime");
                            dataList.add(new ItemData(title, content, imgUrl, viewsNumber,createTime));
                        }
                        itemList.add(new Item(dictCode, dictLabel, dataList));
                    }
                    handler.sendEmptyMessage(3);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
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
                case 3:
                    for (int i = 0; i < itemList.size() ; i++) {
                        tab_home.addTab(tab_home.newTab().setText(itemList.get(i).getLabel()).setTag(itemList.get(i).getCode()));
                    }
                    listXinwen_home.setLayoutManager(new GridLayoutManager(getActivity(),1));
                    listXinwen_home.setAdapter(new ItemAdapter(itemList.get(0).getDataList(),R.layout.item_itemdata));
                    break;
                case 4:
                    int position = (int)msg.obj;
                    listXinwen_home.setLayoutManager(new GridLayoutManager(getActivity(),1));
                    listXinwen_home.setAdapter(new ItemAdapter(itemList.get(position).getDataList(),R.layout.item_itemdata));
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        thread.interrupt();
        thread1.interrupt();
        thread2.interrupt();
    }
}