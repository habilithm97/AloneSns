package com.example.alonesns.Presenter;

import com.example.alonesns.Model.MainModel;
import com.example.alonesns.View.HomeFragment;
import com.example.alonesns.View.NewPostActivity;

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
        MainModel item = new MainModel();
        item.setDate(NewPostActivity.date);
        item.setPicture(NewPostActivity.picturePath);
        item.setContent(NewPostActivity.content);

        HomeFragment.roomDB.mainDao().insert(item);
        HomeFragment.items.clear();
        HomeFragment.items.addAll(HomeFragment.roomDB.mainDao().getAll());
        HomeFragment.adapter.notifyDataSetChanged();
    }
}