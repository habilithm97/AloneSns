package com.example.alonesns.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alonesns.AppConstants;
import com.example.alonesns.MyDatabase;
import com.example.alonesns.Presenter.NewPostContract;
import com.example.alonesns.Presenter.NewPostPresenter;
import com.example.alonesns.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewPostActivity extends AppCompatActivity implements NewPostContract.View {
    private static final String TAG = "NewPostActivity";

    private NewPostContract.Presenter presenter;

    EditText contentEdt;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    TextView dateTv;

    private static final int REQUEST_CODE = 0;

    ImageView imageView;

    Context context;

    Bitmap bitmap;

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

        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getPhoto();
            }
        });

        contentEdt = (EditText)findViewById(R.id.contentEdt);
        contentEdt.setInputType(0); // 액티비티 실행 시 키보드가 자동으로 올라오는 현상 방지, 0은 null
        contentEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.edtAction();
            }
        });

        Button uploadBtn = (Button)findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.uploadAction();
            }
        });

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
        Intent intent = new Intent();
        intent.setType("image/*"); // 인텐트 타입은 이미지
        intent.setAction(Intent.ACTION_GET_CONTENT); // 이미지 가져오기
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵을 생성하고 그 비트맵을 이미지뷰에 표시함
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(in);
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {}
            } else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "이미지 선택 취소", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        saveData();
    }

    public void saveData() {
        String date = dateTv.getText().toString();
        String picturePath = savePicture();
        String content = contentEdt.getText().toString();

        String sql = "insert into " + MyDatabase.TABLE_NAME + "(DATE, PICTURE, CONTENT) values(" +
                "'" + date + "', " +
                "'" + picturePath + "', " +
                "'" + content + "')";

        Log.d(TAG, "sql : " + sql);
        MyDatabase database = MyDatabase.getInstance(context);
        database.execSQL(sql);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private String savePicture() {
        if(bitmap == null) {
            Log.d(TAG, "저장할 사진이 없음. ");
            return "";
        }
        // 이미지는 폴더를 만들어 저장하고, 이미지 경로만 데이터베이스에 저장함
        File photoFolder = new File(AppConstants.PHOTO_FOLDER); // 이미지를 저장할 폴더
        if(!photoFolder.isDirectory()) {
            photoFolder.mkdir();
            Log.d(TAG, "이미지 폴더 생성 : " + photoFolder);
        }
        String photoFileName = createFileName(); // 현재 날짜를 이미지 파일 이름으로 함
        String picturePath = photoFolder + File.separator + photoFileName; // 이미지 경로

        try {
            FileOutputStream out = new FileOutputStream(picturePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // 이미지를 압축(100이면 그대로)
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return picturePath;
    }

    private String createFileName() {
        Date curDate = new Date();
        String curDateStr = String.valueOf(curDate.getTime());
        return curDateStr;
    }

    @Override
    public void cancelResult() {
        finish();
    }
}