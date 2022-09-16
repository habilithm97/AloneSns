package com.example.alonesns.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.alonesns.AppConstants;
import com.example.alonesns.Home.HomeFragment;
import com.example.alonesns.My.MyFragment;
import com.example.alonesns.NewPost.NewPostActivity;
import com.example.alonesns.R;
import com.example.alonesns.Setting.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    MainContract.Presenter presenter;

    HomeFragment homeFragment;
    MyFragment myFragment;
    SettingFragment settingFragment;

    BottomNavigationView bottomNavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        init();
    }

    private void init() {
        homeFragment = new HomeFragment();
        myFragment = new MyFragment();
        settingFragment = new SettingFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavi = findViewById(R.id.bottomNavi);
        bottomNavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;

                    case R.id.tab2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).commit();
                        return true;

                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingFragment).commit();
                        return true;
                }
                return false;
            }
        });
        setPicturePath(); // 사진 경로 접근 및 폴더 없으면 생성

        presenter.onTabItemSelectedListener();
    }

    public void setPicturePath() {
        String folderPath = getFilesDir().getAbsolutePath(); // 내부 저장소 파일 경로 접근
        AppConstants.PHOTO_FOLDER = folderPath + File.separator + "photo";

        File photoFolder = new File(AppConstants.PHOTO_FOLDER);
        if(!photoFolder.exists()) {
            photoFolder.mkdir();
        }
    }

    @Override
    public void onTabSelected(int position) {
        if(position == 0) {
            bottomNavi.setSelectedItemId(R.id.tab1);
        } else if(position == 1) {
            bottomNavi.setSelectedItemId(R.id.tab2);
        } else if(position == 2) {
            bottomNavi.setSelectedItemId(R.id.tab3);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newPost:
                presenter.menuAction();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void newPostIntent() {
        Intent intent = new Intent(getApplicationContext(), NewPostActivity.class);
        startActivity(intent);
    }
}