package com.example.usedbookstore.Presenter;

import com.example.usedbookstore.Model.MainModel;

// Model과 View를 연결하여 동작을 처리함
public class MainPresenter implements Contract.Presenter {
    Contract.View view;
    MainModel mainModel;

    public MainPresenter(Contract.View view) {
        this.view = view; // 액티비티 View 정보를 가져와서 통신함
        mainModel = new MainModel(this); // Model 객체 생성
    }

    @Override
    public void LoginAction() {
        view.LoginResult();
    }
}