package com.example.alonesns.View;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.alonesns.Presenter.MainContract;
import com.example.alonesns.Presenter.MainPresenter;
import com.example.alonesns.R;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this); // Presenter와 통신하기 위한 객체 생성
        init();
    }

    private void init() {

    }
}