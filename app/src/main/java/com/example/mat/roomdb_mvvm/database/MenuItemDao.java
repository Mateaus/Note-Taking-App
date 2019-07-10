package com.example.mat.roomdb_mvvm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;

import java.util.List;

@Dao
public interface MenuItemDao {

    @Insert
    void insert(DrawerMenuItem drawerMenuItem);

    @Update
    void update(DrawerMenuItem drawerMenuItem);

    @Delete
    void delete(DrawerMenuItem drawerMenuItem);

    @Query("SELECT * FROM menu_item WHERE menu_item_id <= 2")
    LiveData<List<DrawerMenuItem>> getMenuItems();

    @Query("SELECT * FROM menu_item WHERE menu_item_id > 2 ORDER BY menu_item_id DESC")
    LiveData<List<DrawerMenuItem>> getTagMenuItems();

    @Query("SELECT COUNT() FROM note_table")
    LiveData<Integer> getNumberOfNotes();

    @Query("SELECT COUNT() FROM note_table WHERE note_tag=:tag")
    LiveData<Integer> getNumberOfNotesByTag(String tag);
}
