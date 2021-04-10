package com.example.smartcity_test5.ui.personal;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartcity_test5.MainActivity;
import com.example.smartcity_test5.R;

public class PersonalFragment extends Fragment {

    private PersonalViewModel mViewModel;

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    private ActionBar actionBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.personal_fragment, container, false);
        actionBar =((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("个人中心");

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PersonalViewModel.class);
        // TODO: Use the ViewModel
    }

}