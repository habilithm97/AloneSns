package com.example.usedbookstore.Model;

import com.example.usedbookstore.Presenter.Contract;

// 데이터 관리를 해줄 Model 클래스
public class MainModel {
    Contract.Presenter presenter;

    public MainModel(Contract.Presenter presenter){
        this.presenter = presenter;
    }

}