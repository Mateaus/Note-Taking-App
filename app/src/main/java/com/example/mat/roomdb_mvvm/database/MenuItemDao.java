package com.example.mat.roomdb_mvvm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerLayoutMenuItem;

import java.util.List;

@Dao
public interface MenuItemDao {

    @Insert
    void insert(DrawerLayoutMenuItem drawerLayoutMenuItem);

    @Update
    void update(DrawerLayoutMenuItem drawerLayoutMenuItem);

    @Delete
    void delete(DrawerLayoutMenuItem drawerLayoutMenuItem);

    @Query("SELECT * FROM menu_item WHERE menu_item_id <= 2")
    LiveData<List<DrawerLayoutMenuItem>> getMenuItems();

    @Query("SELECT * FROM menu_item WHERE menu_item_id > 2 ORDER BY menu_item_id DESC")
    LiveData<List<DrawerLayoutMenuItem>> getTagMenuItems();

    @Query("SELECT COUNT() FROM note_table")
    LiveData<Integer> getNumberOfNotes();

    @Query("SELECT COUNT() FROM note_table WHERE note_tag=:tag")
    LiveData<Integer> getNumberOfNotesByTag(String tag);
}
