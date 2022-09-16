package com.example.alonesns.NewPost;

import com.example.alonesns.Home.HomeFragment;
import com.example.alonesns.Model.MainModel;

public class NewPostPresenter implements NewPostContract.Presenter {
    NewPostContract.View view;

    public NewPostPresenter(NewPostContract.View view) {
        this.view = view;
    }

    @Override
    public void getDate() {
        view.setDate();
    }

    @Override
    public void dialogAction(int id) {
        view.showPhotoMenuDialog(id);
    }

    @Override
    public void edtAction() {
        view.edtControl();
    }

    @Override
    public void cancelAction() {
        view.cancelResult();
    }

    public void saveData() {
        // Entity에 데이터를 설정
        MainModel item = new MainModel();
        item.setDate(NewPostActivity.date);
        item.setPicture(NewPostActivity.picturePath);
        item.setContent(NewPostActivity.content);

        // RoomDB에 아이템을 추가 -> 리스트 초기화 -> 리스트 모두 가져오기 -> 어댑터를 이용해 리스트로 표시
        HomeFragment.roomDB.mainDao().insert(item);
        HomeFragment.items.clear();
        HomeFragment.items.addAll(HomeFragment.roomDB.mainDao().getAll());
        HomeFragment.adapter.notifyDataSetChanged();
    }
}