package com.example.alonesns.Presenter;

import com.example.alonesns.Model.MainModel;

import java.util.ArrayList;
import java.util.List;

// Model과 View를 연결하여 동작을 처리함
public class NewPostPresenter implements NewPostContract.Presenter {
    NewPostContract.View view;

    public NewPostPresenter(NewPostContract.View view) {
        this.view = view; // 액티비티 View 정보를 가져와서 통신함
    }

    @Override
    public void getDate() {
        view.setDate();
    }

    @Override
    public void getPhoto() {
        view.setPhoto();
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
}