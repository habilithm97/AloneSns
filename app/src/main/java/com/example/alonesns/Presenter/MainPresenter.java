package com.example.alonesns.Presenter;

import com.example.alonesns.Model.MainModel;

// Model과 View를 연결하여 동작을 처리함
public class MainPresenter implements MainContract.Presenter {
    MainContract.View view;
    MainModel mainModel;
    int position;

    public MainPresenter(MainContract.View view) {
        this.view = view; // 액티비티 View 정보를 가져와서 통신함
        mainModel = new MainModel(); // Model 객체 생성
    }

    @Override
    public void onTabItemSelectedListener() {
        view.onTabSelected(position);
    }
}