package com.example.alonesns;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.alonesns.Model.MainModel;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {

    @Insert(onConflict = REPLACE) // update와 동일한 기능을 할 수 있음
    void insert(MainModel mainModel);

    @Delete
    void delete(MainModel mainModel);

    @Query("UPDATE my_tb SET content = :str WHERE _id = :sID") // 아이디를 기준으로 글 내용 수정(사진과 날짜는 변경 불가)
    void update(int sID, String str);

    @Query("SELECT * FROM my_tb") // 테이블의 모든 컬럼을 조회
    List<MainModel> getAll();
}
