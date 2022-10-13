package com.example.alonesns.NewPost;

public interface NewPostContract {

    interface View {
        void setDate();
        void showPhotoMenuDialog(int id);
        void cancelResult();
    }

    interface Presenter {
        void getDate();
        void dialogAction(int id);
        void cancelAction();

        void saveData();
    }
}