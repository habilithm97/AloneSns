package com.example.alonesns.My;

import com.example.alonesns.Model.MainModel;

public class MyPresenter implements MyContract.Presenter {
    MyContract.View view;
    MainModel mainModel;

    public MyPresenter(MyContract.View view) {
        this.view = view;
        mainModel = new MainModel();
    }
}