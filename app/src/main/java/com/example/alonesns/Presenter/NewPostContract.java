package com.example.alonesns.Presenter;

import com.example.alonesns.Model.MainModel;

import java.util.List;

// View와 Presenter를 연결하기 위한 상호작용 인터페이스
public interface NewPostContract {

    interface View {
        void setDate();
        void setPhoto();
        void edtControl();
        void uploadResult();
        void cancelResult();
    }

    interface Presenter {
        void getDate();
        void getPhoto();
        void edtAction();
        void uploadAction();
        void cancelAction();
    }
}