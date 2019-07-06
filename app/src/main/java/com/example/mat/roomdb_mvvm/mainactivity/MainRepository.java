package com.example.mat.roomdb_mvvm.mainactivity;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.mat.roomdb_mvvm.database.MenuItemDao;
import com.example.mat.roomdb_mvvm.database.NoteDao;
import com.example.mat.roomdb_mvvm.database.NoteDatabase;
import com.example.mat.roomdb_mvvm.database.TagCategoryDao;
import com.example.mat.roomdb_mvvm.mainactivity.entity.TagCategory;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerLayoutMenuItem;
import com.example.mat.roomdb_mvvm.mainactivity.model.MergedMenu;

import java.util.List;

public class MainRepository {

    private MenuItemDao menuItemDao;
    private NoteDao noteDao;
    private TagCategoryDao tagCategoryDao;
    private LiveData<List<DrawerLayoutMenuItem>> allMenuItems;
    private LiveData<Integer> allNotesSize;
    private LiveData<List<TagCategory>> allTagCategories;


    private LiveData<Integer> allNoteSize;
    private LiveData<Integer> allFavoriteNoteSize;
    private LiveData<List<Integer>> allTagNotesSizeList;

    private MergedMenu mergedMenu = new MergedMenu();

    public MainRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        menuItemDao = noteDatabase.menuItemDao();
        noteDao = noteDatabase.noteDao();
        tagCategoryDao = noteDatabase.categoryDao();
        allMenuItems = menuItemDao.getMenuItems();
        allNotesSize = menuItemDao.getNumberOfNotes();
        allTagCategories = tagCategoryDao.getAllTagCategories();

        allNoteSize = noteDao.getAllNotesSize();
        allFavoriteNoteSize = noteDao.getAllFavoriteNotesSize();
        allTagNotesSizeList = noteDao.getAllTagNotesSizeList();
    }

    public LiveData<DrawerLayoutMenuItem> getMenuOne() {
        return noteDao.getMenuOne();
    }

    public LiveData<DrawerLayoutMenuItem> getMenuTwo() {
        return noteDao.getMenuTwo();
    }

    public LiveData<List<DrawerLayoutMenuItem>> getTagMenus() {
        return noteDao.getTagMenus();
    }


    private MediatorLiveData<MergedMenu> getMergeData() {
        MediatorLiveData<MergedMenu> mergedMenuMediatorLiveData = new MediatorLiveData<>();
        mergedMenuMediatorLiveData.addSource(getMenuOne(), new Observer<DrawerLayoutMenuItem>() {
            @Override
            public void onChanged(DrawerLayoutMenuItem drawerLayoutMenuItem) {
                mergedMenu.setMenuOne(drawerLayoutMenuItem);
                mergedMenuMediatorLiveData.setValue(mergedMenu);
            }
        });

        mergedMenuMediatorLiveData.addSource(getMenuTwo(), new Observer<DrawerLayoutMenuItem>() {
            @Override
            public void onChanged(DrawerLayoutMenuItem drawerLayoutMenuItem) {
                mergedMenu.setMenuTwo(drawerLayoutMenuItem);
                mergedMenuMediatorLiveData.setValue(mergedMenu);
            }
        });
        return mergedMenuMediatorLiveData;
    }

    public LiveData<MergedMenu> getMergedMenuLiveData() {
        return getMergeData();
    }

    public LiveData<Integer> getAllNoteSize() {
        return allNoteSize;
    }

    public LiveData<Integer> getAllFavoriteNoteSize() {
        return allFavoriteNoteSize;
    }

    public LiveData<List<Integer>> getAllTagNotesSizeList() {
        return allTagNotesSizeList;
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
