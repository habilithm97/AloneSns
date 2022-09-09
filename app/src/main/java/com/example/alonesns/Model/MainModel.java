package com.example.alonesns.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.alonesns.Presenter.LoginContract;
import com.example.alonesns.Presenter.MainContract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "my_tb")
public class MainModel implements Serializable {

    @PrimaryKey(autoGenerate = true) // 데이터가 생성될 때마다 자동으로 고유 id 값이 1씩 증가함
    private int _id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "picture")
    private String picture;

    @ColumnInfo(name = "content")
    private String content;

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