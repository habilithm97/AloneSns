package com.example.alonesns.Presenter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alonesns.Model.MainModel;
import com.example.alonesns.MyDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements HomeContract.Presenter {
    private static final String TAG = "HomePresenter";

    HomeContract.View view;

    Context context;

    public List<MainModel> items;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    public void loadDatabase() {
        String sql = "select _id, DATE, PICTURE, CONTENT from " + MyDatabase.TABLE_NAME + " order by DATE desc";

        MyDatabase database = MyDatabase.getInstance(context);
        if (database != null) {
            Cursor cursor = database.rawQuery(sql);
            int recordCount = cursor.getCount();
            items = new ArrayList<>();

            for (int i = 0; i < recordCount; i++) {
                cursor.moveToNext();

                int _id = cursor.getInt(0);
                String date = cursor.getString(1);
                String picture = cursor.getString(2);
                String content = cursor.getString(3);

                Log.d(TAG, "#" + i + " -> " + _id + ", " + date + ", " + picture + ", " + content);
                //items.add(new MainModel(_id, date, picture, content));
            }
            cursor.close(); // 커서 사용 후에는 닫아야 됨
            view.setData(items);
        }
    }
}