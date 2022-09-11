package com.example.alonesns;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.alonesns.Model.MainModel;

@Database(entities = {MainModel.class}, version = 1, exportSchema = false) // exportSchema : 스키마를 폴더로 내보내도록 설정 여부를 정함
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB database;
    private static String DATABASE_NAME = "my_db";

    public synchronized static RoomDB getInstance(Context context) {
        if(database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries() // 메인 스레드에 접근할 수 있음
                    .fallbackToDestructiveMigration() // 스키마 버전 변경 가능
                    .build();
        }
        return database;
    }
    public abstract MainDao mainDao();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {}
}
