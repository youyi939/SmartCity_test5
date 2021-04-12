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
import com.example.smartcity_test5.MainActivity;
import com.example.smartcity_test5.R;
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

        return root;
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