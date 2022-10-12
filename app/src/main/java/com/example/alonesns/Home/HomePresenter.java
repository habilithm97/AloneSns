package com.example.alonesns.Home;

import com.example.alonesns.Model.MainModel;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter {
    HomeContract.View view;
    MainModel mainModel;

    public static List<MainModel> items;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
        mainModel = new MainModel();
    }
}