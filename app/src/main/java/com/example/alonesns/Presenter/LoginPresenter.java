package com.example.alonesns.Presenter;

import com.example.alonesns.Model.MainModel;

// Model과 View를 연결하여 동작을 처리함
public class LoginPresenter implements LoginContract.Presenter {
    LoginContract.View view;
    MainModel mainModel;

    public LoginPresenter(LoginContract.View view) {
        this.view = view; // 액티비티 View 정보를 가져와서 통신함
        mainModel = new MainModel(); // Model 객체 생성
    }

    @Override
    public void LoginAction() {
        view.LoginResult();
    }
}