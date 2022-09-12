package com.example.alonesns.Presenter;

import com.example.alonesns.Model.MainModel;

public interface NewPostContract {

    interface View {
        void setDate();
        void showPhotoMenuDialog(int id);
        void edtControl();
        void cancelResult();
    }

    interface Presenter {
        void getDate();
        void dialogAction(int id);
        void edtAction();
        void cancelAction();

        void saveData();
    }
}