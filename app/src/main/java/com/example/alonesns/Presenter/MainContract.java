package com.example.alonesns.Presenter;

import com.example.alonesns.Model.MainModel;

import java.util.List;

// View와 Presenter를 연결하기 위한 상호작용 인터페이스
public interface MainContract {

    interface View {
        void onTabSelected(int position);
        void loadData(List<MainModel> items);
        void newPostIntent();
    }

    interface Presenter {
        void onTabItemSelectedListener();
        void menuAction();
    }
}