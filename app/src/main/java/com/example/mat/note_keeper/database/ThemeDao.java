package com.example.mat.note_keeper.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mat.note_keeper.color.entity.Theme;

@Dao
public interface ThemeDao {

    @Insert
    void insert(Theme theme);

    @Update
    void update(Theme theme);

    @Delete
    void delete(Theme theme);

    @Query("SELECT * FROM theme_table")
    LiveData<Theme> getTheme();
}