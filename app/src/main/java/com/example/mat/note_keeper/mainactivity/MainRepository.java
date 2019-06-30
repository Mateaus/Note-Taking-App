package com.example.mat.note_keeper.mainactivity;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mat.note_keeper.database.NoteDatabase;
import com.example.mat.note_keeper.database.TagCategoryDao;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;

import java.util.List;

public class MainRepository {

    private TagCategoryDao tagCategoryDao;
    private LiveData<List<TagCategory>> allTagCategories;

    public MainRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        tagCategoryDao = noteDatabase.categoryDao();
        allTagCategories = tagCategoryDao.getAllTagCategories();
    }

    public LiveData<List<TagCategory>> getAllTagCategories() {
        return allTagCategories;
    }
}
