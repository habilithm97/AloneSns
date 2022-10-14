package com.example.alonesns.NewPost;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alonesns.AppConstants;
import com.example.alonesns.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewPostActivity extends AppCompatActivity implements NewPostContract.View {
    private static final String TAG = "NewPostActivity";

    NewPostContract.Presenter presenter;

    EditText contentEdt;
    TextView dateTv;
    ImageView imageView;

    boolean isPhotoCaptured;
    boolean isPhotoFileSaved;
    boolean isPhotoCanceled;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    Bitmap resultBitmap;
    int selectedPhotoMenu;
    AlertDialog.Builder builder;

    public static String date;
    public static String picturePath;
    public static String content;

    public static Bitmap rotatedBitmap;

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
                if(isPhotoCaptured || isPhotoFileSaved) {
                    presenter.dialogAction(AppConstants.PHOTO); // 이미지가 있을 때
                } else {
                    presenter.dialogAction(AppConstants.NOT_PHOTO); // 이미지가 없을 때
                }
            }
        });

        contentEdt = (EditText)findViewById(R.id.contentEdt);

        Button uploadBtn = (Button)findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = dateTv.getText().toString();
                picturePath = savePicture();
                content = contentEdt.getText().toString();

                if(isPhotoFileSaved) {
                    presenter.saveData();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "업로드할 이미지가 없습니다. ", Toast.LENGTH_SHORT).show();
                }
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
    public void showPhotoMenuDialog(int id) {
        switch (id) {
            case AppConstants.PHOTO: // 이미지뷰에 이미지가 있는 경우
                builder = new AlertDialog.Builder(this);
                builder.setTitle("이미지 메뉴 선택");

                // .setSingleChoiceItems() : 대화 상자 내용에 표시할 아이템 리스트와 아이템을 클릭했을 때 반응할 리스너를 설정함
                builder.setSingleChoiceItems(R.array.array_photo, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichBtn) {
                        selectedPhotoMenu = whichBtn;
                    }
                });

                builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (selectedPhotoMenu == 0) { // 앨범에서 선택하기
                            showPhotoSelectionActivity();
                        } else if (selectedPhotoMenu == 1) { // 삭제하기
                            imageView.setImageResource(R.drawable.find_image);
                            // 이미지가 삭제되었기 때문에 이미지 유무 상태를 변경함
                            isPhotoCanceled = true;
                            isPhotoFileSaved = false;
                        }
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                break;

                case AppConstants.NOT_PHOTO: // 이미지뷰에 이미지가 없는 경우
                    builder = new AlertDialog.Builder(this);
                    builder.setTitle("이미지 메뉴 선택");

                    builder.setSingleChoiceItems(R.array.array_not_photo, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichBtn) {
                            selectedPhotoMenu = whichBtn;
                        }
                    });

                    builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(selectedPhotoMenu == 0) { // 앨범에서 선택하기
                                showPhotoSelectionActivity();
                            }
                        }
                    });

                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
                    break;
            default:
                break;
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String createFileName() {
        Date curDate = new Date();
        String curDateStr = String.valueOf(curDate.getTime());
        return curDateStr;
    }

    public void showPhotoSelectionActivity() { // 앨범에서 선택하기
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // 외부 스토리지에서 가져오기
        startActivityForResult(intent, AppConstants.PHOTO_SELECTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if(intent != null) {
            switch (requestCode) {
                case AppConstants.PHOTO_SELECTION: // 앨범에서 선택하기 메뉴를 선택했을 경우
                    Uri imageUri = intent.getData(); // 가져올 데이터의 주소
                    String[] filePathColumn = {MediaStore.Images.Media.DATA}; // 가져올 컬럼 이름 목록

                    Cursor cursor = this.getContentResolver().query(imageUri, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]); // 커서를 사용해 컬럼 인덱스 가져오기
                    String filePath = cursor.getString(columnIndex); // 커서를 사용해 가져온 컬럼 인덱스를 문자열로 변환하기
                    cursor.close();

                    resultBitmap = decodeBitmapFromRes(new File(filePath), imageView.getWidth(), imageView.getHeight()); // 가져온 이미지의 크기를 조절한 결과 비트맵

                    ExifInterface exifInterface = null; // 이미지가 가지고 있는 정보의 집합 클래스 ExifInterface
                    try {
                        exifInterface = new ExifInterface(filePath); // 이미지 경로를 이용하여 exifInterface 객체 생성
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                    rotatedBitmap = rotateBitmap(resultBitmap, orientation); // 정방향대로 회전된 이미지
                    imageView.setImageBitmap(rotatedBitmap);
                    isPhotoFileSaved = true;
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    // 대용량 비트맵을 Exception 없이 효과적으로 로딩하기(최적화 로딩)
    public static Bitmap decodeBitmapFromRes(File res, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // decode~ 메서드들은 비트맵을 생성하기 위해 메모리를 할당하려고 시도하고 그 결과 쉽게 OutOfMemory에 도달해버리는데,
        // options를 통해 옵션들을 조정할 수 있음
        options.inJustDecodeBounds = true; // true일 경우, 이미지의 크기만 구해서 옵션에 설정함
        BitmapFactory.decodeFile(res.getAbsolutePath(), options);
        /*
        *int inSampleSize
         -이미지 파일을 디코딩할 때 원본 이미지 크기대로 디코딩할지, 축소해서 디코딩할지를 지정함
         -값은 1 또는 2의 거듭 제곱 값이 들어갈 수 있음(그 외의 값일 경우 가장 가까운 2의 거듭 제곱 값으로 내림되어 실행됨
         -값이 1일 경우(또는 1보다 작을 경우) : 원본 이미지 크기로
         -값이 2일 경우(또는 2보다 클 경우) : 가로/세로 픽셀을 해당 값 만큼 나눈 크기로
          ex) 값을 2로 지정했을 경우 이미지의 가로/세로 값이 각각 2로 나누어진 크기로 설정되어 실제 이미지 크기는 원본 이미지의 1/4가 됨
        */
        options.inSampleSize = calInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false; // 로드하기 위해서 true에서 false로 설정함
        return BitmapFactory.decodeFile(res.getAbsolutePath(), options);
    }

    // 로드하려는 이미지가 실제 필요한 사이즈보다 큰지 체크하고
    // 실제 필요한 사이즈로 이미지를 조절하기
    public static int calInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) { // reqWidth와 reqHeight는 필요한 사이즈, 즉 이미지뷰 사이즈임
        final int height = options.outHeight; // 높이
        final int width = options.outWidth; // 너비
        int inSampleSize = 1; // 원본 이미지

        if(height > reqHeight || width > reqWidth) { // 필요한 사이즈(이미지뷰)보다 크면
            final int halfHeight = height;
            final int halfWidth = width;

            while((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2; // 이미지 축소
            }
        }
        return inSampleSize;
    }

    private String savePicture() {
        if(rotatedBitmap == null) {
            Log.d(TAG, "저장할 이미지가 없음. ");
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
            rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // 이미지를 압축(100이면 그대로) -> rotateBitmap은 이미 압축 과정이 끝남
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return picturePath;
    }

    @Override
    public void cancelResult() {
        finish();
    }
}