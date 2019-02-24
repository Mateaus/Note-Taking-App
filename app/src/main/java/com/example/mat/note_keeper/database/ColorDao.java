package com.example.mat.note_keeper.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.mat.note_keeper.color.entity.Color;

@Dao
public interface ColorDao {

    @Insert
    void insert(Color color);

    @Update
    void update(Color color);

    @Delete
    void delete(Color color);

    @Query("SELECT * FROM color_table")
    LiveData<Color> getSelectedColors();
}
