package com.example.mat.note_keeper.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mat.note_keeper.mainactivity.model.MenuItem;

import java.util.List;

@Dao
public interface MenuItemDao {

    @Insert
    void insert(MenuItem menuItem);

    @Update
    void update(MenuItem menuItem);

    @Delete
    void delete(MenuItem menuItem);

    @Query("SELECT * FROM menu_item")
    LiveData<List<MenuItem>> getMenuItems();

    @Query("SELECT COUNT() FROM note_table")
    LiveData<Integer> getNumberOfNotes();

    @Query("SELECT COUNT() FROM note_table WHERE note_tag=:tag")
    LiveData<Integer> getNumberOfNotesByTag(String tag);
}
