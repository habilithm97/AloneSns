package com.example.alonesns.Presenter;

import com.example.alonesns.Model.MainModel;

import java.util.List;

public interface HomeContract {

    interface View {
        void setData(List<MainModel> items);
    }

    interface Presenter {
    }
}