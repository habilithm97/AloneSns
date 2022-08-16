package com.example.alonesns.Presenter;

// View와 Presenter를 연결하기 위한 상호작용 인터페이스
public interface MainContract {

    interface View {
        void onTabSelected(int position);
    }

    interface Presenter {
        void onTabItemSelectedListener();
    }
}