package com.example.alonesns.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alonesns.Main.MainActivity;
import com.example.alonesns.R;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);
        init();
    }

    private void init() {
        EditText emailEdt = (EditText)findViewById(R.id.emailEdt);
        EditText pwEdt = (EditText)findViewById(R.id.pwEdt);

        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.LoginAction();
            }
        });

        Button registerBtn = (Button)findViewById(R.id.registerBtn);
    }

    @Override
    public void LoginResult() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}