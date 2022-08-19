package com.example.alonesns.Model;

import com.example.alonesns.Presenter.LoginContract;
import com.example.alonesns.Presenter.MainContract;

import java.util.ArrayList;
import java.util.List;

// 데이터 관리를 해줄 Model 클래스
public class MainModel {
    private String content;
    private String date;

    public MainModel(String content, String date) {
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}