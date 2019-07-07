package com.example.mat.roomdb_mvvm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mat.roomdb_mvvm.mainactivity.entity.TagCategory;

import java.util.List;

public interface TagCategoryDao {

    @Insert
    void insert(TagCategory tagCategory);

    @Update
    void update(TagCategory tagCategory);

    @Delete
    void delete(TagCategory tagCategory);

    LiveData<List<TagCategory>> getAllTagCategories();
}
