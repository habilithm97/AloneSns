package com.example.alonesns;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyDatabase {
    private static final String TAG = "MyDatabase";

    private static MyDatabase database;
    public static String DATABASE_NAME = "mydb.db";
    public static String TABLE_NAME = "mytb";
    public static int DB_VERSION = 1;

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    private MyDatabase(Context context) {
        this.context = context;
    }

    public static MyDatabase getInstance(Context context) {
        if(database == null) {
            database = new MyDatabase(context);
        }
        return database;
    }

    public boolean open() {
        println("[ " + DATABASE_NAME + " ] 데이터베이스가 오픈됨. ");

        // 헬퍼 객체 생성하고 db 객체 참조함
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        return true;
    }

    public void close() {
        println("[ " + DATABASE_NAME + " ] 데이터베이스를 닫음. ");

        sqLiteDatabase.close();
        database = null;
    }

    public Cursor rawQuery(String SQL) { // SELECT 명령어를 사용하여 query를 실행함
        println("\nexecuteQuery가 호출됨. ");

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery(SQL, null);
            println("cursor count : " + cursor.getCount());
        } catch (Exception e) {
            Log.e(TAG, "executeQuery 호출 중 오류가 발생함. ", e);
        }
        return cursor;
    }

    public boolean execSQL(String SQL) { // SELECT 명령을 제외한 모든 SQL 문장을 실행함
        println("\nexecute 호출됨. ");

        try {
            sqLiteDatabase.execSQL(SQL);
            Log.d(TAG, "SQL : " + SQL);
        } catch (Exception e) {
            Log.e(TAG, "execute 호출 중 오류가 발생함.", e);
        }
        return true;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            println("[ " + DATABASE_NAME + " ] 데이터베이스 생성됨. ");
            println("[ " + TABLE_NAME + " ] 테이블 생성됨. ");

            // 테이블 삭제
            String DROP_SQL = "drop table if exists " + TABLE_NAME;
            try {
                db.execSQL(DROP_SQL);
            } catch (Exception e) {
                Log.e(TAG, "테이블을 삭제하는데에 오류가 발생함. ");
            }

            // 테이블 생성
            String CREATE_SQL = "create table " + TABLE_NAME + "("
                    + "  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "  DATE TEXT DEFAULT '', "
                    + "  PICTURE TEXT DEFAULT '', "
                    + "  CONTENT TEXT DEFAULT '' " + ")";
            try {
                db.execSQL(CREATE_SQL);
            } catch (Exception e) {
                Log.e(TAG, "테이블을 생성하는데에 오류가 발생함. ");
            }

            String CREATE_INDEX_SQL = "create index " + TABLE_NAME + "_IDX ON " + TABLE_NAME + "(" + "DATE" + ")";
            try {
                db.execSQL(CREATE_INDEX_SQL);
            } catch (Exception e) {
                Log.e(TAG, "테이블 인덱스를 생성하는데에 오류가 발생함. ");
            }
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            println("[ " + DATABASE_NAME + " ] 데이터 베이스 오픈됨. ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println(oldVersion + " 버전에서 " + newVersion + " 버전으로 데이터 베이스 업데이트됨. ");
        }
    }

    public void println(String data) {
        Log.d(TAG, data);
    }
}
