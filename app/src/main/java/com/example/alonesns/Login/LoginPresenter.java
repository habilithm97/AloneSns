package com.example.alonesns.Login;


public class LoginPresenter implements LoginContract.Presenter {
    LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void LoginAction() {
        view.LoginResult();
    }
}