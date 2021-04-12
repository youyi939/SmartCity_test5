package com.example.smartcity_test5.ui.XinWen;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartcity_test5.R;
import com.example.smartcity_test5.ui.home.adapter.ItemAdapter;
import com.example.smartcity_test5.ui.home.pojo.Item;
import com.example.smartcity_test5.ui.home.pojo.ItemData;
import com.example.smartcity_test5.util.KenUtil;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class XinwenFragment extends Fragment {

    private XinwenViewModel mViewModel;

    public static XinwenFragment newInstance() {
        return new XinwenFragment();
    }

    private Unbinder unbinder;
    @BindView(R.id.tab_home)
    TabLayout tab_home;
    @BindView(R.id.listXinwen_home)
    RecyclerView listXinwen_home;

    private List<Item> itemList = new ArrayList<>();
    private Thread thread;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.xinwen_fragment, container, false);
        unbinder = ButterKnife.bind(this,root);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(XinwenViewModel.class);
        // TODO: Use the ViewModel
    }

    public void getItem() {
        thread = new Thread(new Runnable() {
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


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
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
    }
}