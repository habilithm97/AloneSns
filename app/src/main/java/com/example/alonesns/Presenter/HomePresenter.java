package com.example.alonesns.Presenter;

import com.example.alonesns.Model.MainModel;

import java.util.ArrayList;
import java.util.List;

// Model과 View를 연결하여 동작을 처리함
public class HomePresenter implements HomeContract.Presenter {
    HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view; // 액티비티 View 정보를 가져와서 통신함
    }

    public void setData() {
        List<MainModel> items = new ArrayList<>();
        items.add(new MainModel("오늘 하루도 열심히", "2022-08-19"));
        items.add(new MainModel("청년다방 떡볶이와 버갈튀 존맛탱탱구리탱", "2022-08-19"));
        items.add(new MainModel("오오티디", "2022-08-19"));
        items.add(new MainModel("안드로이드 앱개발", "2022-08-19"));
        items.add(new MainModel("메이플스토리 강원기", "2022-08-19"));

        view.loadData(items);
    }
}