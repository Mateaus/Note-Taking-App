package com.example.mat.note_keeper.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mat.note_keeper.mainactivity.entity.TagCategory;

import java.util.List;

@Dao
public interface TagCategoryDao {

    @Insert
    void insert(TagCategory tagCategory);

    @Update
    void update(TagCategory tagCategory);

    @Delete
    void delete(TagCategory tagCategory);

    @Query("SELECT * FROM tag_category")
    LiveData<List<TagCategory>> getAllTagCategories();
}
