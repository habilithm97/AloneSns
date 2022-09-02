package com.example.alonesns.Presenter;

public class MyPresenter implements MyContract.Presenter {
    MyContract.View view;

    public MyPresenter(MyContract.View view) {
        this.view = view;
    }
}