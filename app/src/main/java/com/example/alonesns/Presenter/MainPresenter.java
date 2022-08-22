package com.example.alonesns.Presenter;

import com.example.alonesns.Model.MainModel;

import java.util.ArrayList;
import java.util.List;

// Model과 View를 연결하여 동작을 처리함
public class MainPresenter implements MainContract.Presenter {
    MainContract.View view;
    int position;

    public MainPresenter(MainContract.View view) {
        this.view = view; // 액티비티 View 정보를 가져와서 통신함
    }

    @Override
    public void onTabItemSelectedListener() {
        view.onTabSelected(position);
    }

    @Override
    public void menuAction() {
        view.newPostIntent();
    }
}