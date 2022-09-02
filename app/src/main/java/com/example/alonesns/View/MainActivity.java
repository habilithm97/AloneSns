package com.example.alonesns.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.alonesns.AppConstants;
import com.example.alonesns.MyDatabase;
import com.example.alonesns.Presenter.MainContract;
import com.example.alonesns.Presenter.MainPresenter;
import com.example.alonesns.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private static final String TAG = "MainActivity";

    private MainContract.Presenter presenter;

    HomeFragment homeFragment;
    MyFragment myFragment;
    SettingFragment settingFragment;

    BottomNavigationView bottomNavi;

    MyDatabase database = null;

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
        openDatabase(); // 데이터베이스 오픈

        presenter.onTabItemSelectedListener();
    }

    public void openDatabase() {
        if(database != null) {
            database.close();
            database = null;
        }
        database = MyDatabase.getInstance(this);

        boolean isOpen = database.open();
        if(isOpen) {
            Log.d(TAG,"데이터베이스가 오픈됨. ");
        } else {
            Log.d(TAG, "데이터베이스가 오픈되지 않음. ");
        }
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
    protected void onDestroy() {
        super.onDestroy();

        if(database != null) {
            database.close();
            database = null;
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