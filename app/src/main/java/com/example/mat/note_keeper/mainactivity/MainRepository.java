package com.example.mat.note_keeper.mainactivity;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mat.note_keeper.database.MenuItemDao;
import com.example.mat.note_keeper.database.NoteDatabase;
import com.example.mat.note_keeper.database.TagCategoryDao;
import com.example.mat.note_keeper.mainactivity.entity.Tag;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;
import com.example.mat.note_keeper.mainactivity.model.MenuItem;

import java.util.List;

public class MainRepository {

    private MenuItemDao menuItemDao;
    private TagCategoryDao tagCategoryDao;
    private LiveData<List<MenuItem>> allMenuItems;
    private LiveData<Integer> allNotesSize;
    private LiveData<List<TagCategory>> allTagCategories;

    public MainRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        menuItemDao = noteDatabase.menuItemDao();
        tagCategoryDao = noteDatabase.categoryDao();
        allMenuItems = menuItemDao.getMenuItems();
        allNotesSize = menuItemDao.getNumberOfNotes();
        allTagCategories = tagCategoryDao.getAllTagCategories();
    }

    public LiveData<List<MenuItem>> getAllMenuItems() {
        return allMenuItems;
    }

    public void updateTagCategory(TagCategory tagCategory) {
        new UpdateTagCategory(tagCategoryDao).execute(tagCategory);
    }

    public void updateMenuItem(MenuItem menuItem) {
        new UpdateMenuItem(menuItemDao).execute(menuItem);
    }

    public LiveData<Integer> getAllNotesSize() {
        return allNotesSize;
    }

    public LiveData<List<TagCategory>> getAllTagCategories() {
        return allTagCategories;
    }

    public static class UpdateTagCategory extends AsyncTask<TagCategory, Void, Void> {

        private TagCategoryDao tagCategoryDao;

        public UpdateTagCategory(TagCategoryDao tagCategoryDao) {
            this.tagCategoryDao = tagCategoryDao;
        }

        @Override
        protected Void doInBackground(TagCategory... tagCategories) {
            this.tagCategoryDao.update(tagCategories[0]);
            return null;
        }
    }

    public static class UpdateMenuItem extends AsyncTask<MenuItem, Void, Void> {

        private MenuItemDao menuItemDao;

        public UpdateMenuItem(MenuItemDao menuItemDao) {
            this.menuItemDao = menuItemDao;
        }

        @Override
        protected Void doInBackground(MenuItem... menuItems) {
            this.menuItemDao.update(menuItems[0]);
            return null;
        }
    }
}
