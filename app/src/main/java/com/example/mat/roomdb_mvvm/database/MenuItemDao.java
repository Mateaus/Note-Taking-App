package com.example.mat.roomdb_mvvm.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;

@Dao
public interface MenuItemDao {

    @Insert
    void insert(DrawerMenuItem drawerMenuItem);

    @Update
    void update(DrawerMenuItem drawerMenuItem);

    @Delete
    void delete(DrawerMenuItem drawerMenuItem);
}
