package com.example.alonesns.Presenter;

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
}