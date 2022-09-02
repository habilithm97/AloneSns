package com.example.alonesns.Presenter;

public interface LoginContract {

    interface View {
        void LoginResult();
    }

    interface Presenter {
        void LoginAction();
    }
}