package com.example.mat.roomdb_mvvm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mat.roomdb_mvvm.color.entity.Color;

import java.util.List;

@Dao
public interface ColorDao {

    @Insert
    void insert(Color color);

    @Update
    void update(Color color);

    @Delete
    void delete(Color color);

    @Query("SELECT * FROM color_table")
    LiveData<List<Color>> getAllColors();
}
