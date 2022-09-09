package com.example.alonesns.Presenter;

import com.example.alonesns.Model.MainModel;
import com.example.alonesns.View.HomeFragment;

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
    public void uploadAction() {
        view.uploadResult();
    }

    @Override
    public void cancelAction() {
        view.cancelResult();
    }

    public void saveDataAction() {
        MainModel item = new MainModel();
        item.setDate(date);
        item.setPicture(picturePath);
        item.setContent(content);

        HomeFragment.roomDB.mainDao().insert(item);
        finish();
        HomeFragment.items.clear();
        HomeFragment.items.addAll(HomeFragment.roomDB.mainDao().getAll());
        adapter.notifyDataSetChanged();
    }
}