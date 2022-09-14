package com.example.alonesns.Login;

public interface LoginContract {

    interface View {
        void LoginResult();
    }

    interface Presenter {
        void LoginAction();
    }
}