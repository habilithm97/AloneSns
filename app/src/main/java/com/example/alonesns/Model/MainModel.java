package com.example.alonesns.Model;

import com.example.alonesns.Presenter.LoginContract;
import com.example.alonesns.Presenter.MainContract;

import java.util.ArrayList;
import java.util.List;

// 데이터 관리를 해줄 Model 클래스
public class MainModel {
    int _id;
    private String date;
    private String picture;
    private String content;

    public MainModel(int _id, String date, String picture, String content) {
        this._id = _id;
        this.date = date;
        this.picture = picture;
        this.content = content;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}