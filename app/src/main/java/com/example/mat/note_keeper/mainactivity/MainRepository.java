package com.example.mat.note_keeper.mainactivity;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mat.note_keeper.database.MenuItemDao;
import com.example.mat.note_keeper.database.NoteDatabase;
import com.example.mat.note_keeper.database.TagCategoryDao;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;
import com.example.mat.note_keeper.mainactivity.model.DrawerLayoutMenuItem;

import java.util.List;

public class MainRepository {

    private MenuItemDao menuItemDao;
    private TagCategoryDao tagCategoryDao;
    private LiveData<List<DrawerLayoutMenuItem>> allMenuItems;
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

    public LiveData<List<DrawerLayoutMenuItem>> getAllMenuItems() {
        return allMenuItems;
    }

    public LiveData<List<DrawerLayoutMenuItem>> getAllTagMenuItems() {
        return menuItemDao.getTagMenuItems();
    }

    public void insertTagMenuItem(DrawerLayoutMenuItem drawerLayoutMenuItem) {
        new InsertMenuItem(menuItemDao).execute(drawerLayoutMenuItem);
    }

    public void updateTagCategory(TagCategory tagCategory) {
        new UpdateTagCategory(tagCategoryDao).execute(tagCategory);
    }

    public void updateMenuItem(DrawerLayoutMenuItem drawerLayoutMenuItem) {
        new UpdateMenuItem(menuItemDao).execute(drawerLayoutMenuItem);
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

    public static class InsertMenuItem extends AsyncTask<DrawerLayoutMenuItem, Void, Void> {

        private MenuItemDao menuItemDao;

        public InsertMenuItem(MenuItemDao menuItemDao) {
            this.menuItemDao = menuItemDao;
        }

        @Override
        protected Void doInBackground(DrawerLayoutMenuItem... drawerLayoutMenuItems) {
            this.menuItemDao.insert(drawerLayoutMenuItems[0]);
            return null;
        }
    }

    public static class UpdateMenuItem extends AsyncTask<DrawerLayoutMenuItem, Void, Void> {

        private MenuItemDao menuItemDao;

        public UpdateMenuItem(MenuItemDao menuItemDao) {
            this.menuItemDao = menuItemDao;
        }

        @Override
        protected Void doInBackground(DrawerLayoutMenuItem... drawerLayoutMenuItems) {
            this.menuItemDao.update(drawerLayoutMenuItems[0]);
            return null;
        }
    }
}
