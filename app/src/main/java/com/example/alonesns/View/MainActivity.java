package com.example.alonesns.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.alonesns.Model.MainModel;
import com.example.alonesns.Presenter.MainContract;
import com.example.alonesns.Presenter.MainPresenter;
import com.example.alonesns.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter presenter;

    HomeFragment homeFragment;
    MyFragment myFragment;
    SettingFragment settingFragment;

    BottomNavigationView bottomNavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this); // Presenter와 통신하기 위한 객체 생성
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

        presenter.onTabItemSelectedListener();
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
    public void loadData(List<MainModel> items) {}
}