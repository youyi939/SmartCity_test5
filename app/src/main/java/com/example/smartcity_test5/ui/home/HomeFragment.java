package com.example.smartcity_test5.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartcity_test5.MainActivity;
import com.example.smartcity_test5.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private ActionBar actionBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        actionBar =((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("智慧城市");

        return root;
    }
}