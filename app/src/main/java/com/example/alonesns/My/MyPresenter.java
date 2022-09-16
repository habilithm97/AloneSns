package com.example.alonesns.My;

public class MyPresenter implements MyContract.Presenter {
    MyContract.View view;

    public MyPresenter(MyContract.View view) {
        this.view = view;
    }
}