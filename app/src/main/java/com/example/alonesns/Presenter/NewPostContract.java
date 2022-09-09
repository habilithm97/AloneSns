package com.example.alonesns.Presenter;

public interface NewPostContract {

    interface View {
        void setDate();
        void showPhotoMenuDialog(int id);
        void edtControl();
        void uploadResult();
        void cancelResult();
    }

    interface Presenter {
        void getDate();
        void dialogAction(int id);
        void edtAction();
        void uploadAction();
        void cancelAction();

        void saveDataAction();
    }
}