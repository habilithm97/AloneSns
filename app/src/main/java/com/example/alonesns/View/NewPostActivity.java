package com.example.alonesns.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alonesns.Presenter.NewPostContract;
import com.example.alonesns.Presenter.NewPostPresenter;
import com.example.alonesns.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewPostActivity extends AppCompatActivity implements NewPostContract.View {

    private NewPostContract.Presenter presenter;

    EditText contentEdt;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    TextView dateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        presenter = new NewPostPresenter(this);
        init();
    }

    public void init() {
        dateTv = (TextView)findViewById(R.id.dateTv);
        presenter.getDate();

        ImageView img = (ImageView)findViewById(R.id.img);

        contentEdt = (EditText)findViewById(R.id.contentEdt);
        contentEdt.setInputType(0); // 액티비티 실행 시 키보드가 자동으로 올라오는 현상 방지, 0은 null
        contentEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.edtAction();
            }
        });

        Button uploadBtn = (Button)findViewById(R.id.uploadBtn);

        Button cancelBtn = (Button)findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.cancelAction();
            }
        });
    }

    @Override
    public void setDate() {
        dateTv.setText(nowDate());
    }

    private String nowDate() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        return dateFormat.format(date);
    }

    @Override
    public void setPhoto() {

    }

    @Override
    public void edtControl() {
        contentEdt.setInputType(1); // 키보드 보이게 하기
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); // 키보드 제어 객체
        manager.showSoftInput(contentEdt, InputMethodManager.SHOW_IMPLICIT); // 키보드 보이게 하기
        // setInputType(1)로 키보드를 보이게 했는데 키보드 제어 객체를 빼지 않은 이유는
        // 빼게 된다면 입력창 터치를 두 번해야 키보드가 올라오기 때문임
    }

    @Override
    public void uploadResult() {

    }

    @Override
    public void cancelResult() {
        finish();
    }
}