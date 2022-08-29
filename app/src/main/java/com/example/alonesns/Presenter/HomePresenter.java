package com.example.alonesns.Presenter;

import android.content.Context;
import android.database.Cursor;

import com.example.alonesns.Model.MainModel;
import com.example.alonesns.MyDatabase;
import com.example.alonesns.View.HomeFragment;

import java.util.ArrayList;
import java.util.List;

// Model과 View를 연결하여 동작을 처리함
public class HomePresenter implements HomeContract.Presenter {
    HomeContract.View view;

    Context context;

    public HomePresenter(HomeContract.View view) {
        this.view = view; // 액티비티 View 정보를 가져와서 통신함
    }

    public void setData() {
        String sql = "select _id, DATE, PICTURE, CONTENT from " + MyDatabase.TABLE_NAME + " order by DATE desc";

        MyDatabase database = MyDatabase.getInstance(context);
        if (database != null) {
            Cursor cursor = database.rawQuery(sql);
            int recordCount = cursor.getCount();
            List<MainModel> items = new ArrayList<>();

            for (int i = 0; i < recordCount; i++) {
                cursor.moveToNext();

                int _id = cursor.getInt(0);
                String date = cursor.getString(1);
                String picture = cursor.getString(2);
                String content = cursor.getString(3);

                items.add(new MainModel(_id, date, picture, content));
            }
            cursor.close(); // 커서 사용 후에는 닫아야 됨
            view.loadData(items);
        }
    }
}