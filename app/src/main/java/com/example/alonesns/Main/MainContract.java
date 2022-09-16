package com.example.alonesns.Main;

public interface MainContract {

    interface View {
        void onTabSelected(int position);
        void newPostIntent();
    }

    interface Presenter {
        void onTabItemSelectedListener();
        void menuAction();
    }
}