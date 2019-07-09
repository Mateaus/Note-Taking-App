package com.example.mat.roomdb_mvvm.mainactivity;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.mat.roomdb_mvvm.database.MenuItemDao;
import com.example.mat.roomdb_mvvm.database.NoteDao;
import com.example.mat.roomdb_mvvm.database.NoteDatabase;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerLayoutMenuItem;
import com.example.mat.roomdb_mvvm.mainactivity.model.MergedMenu;

import java.util.List;

public class MainRepository {

    private MenuItemDao menuItemDao;
    private NoteDao noteDao;
    private LiveData<List<DrawerLayoutMenuItem>> allMenuItems;

    private MergedMenu mergedMenu = new MergedMenu();

    public MainRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        menuItemDao = noteDatabase.menuItemDao();
        noteDao = noteDatabase.noteDao();
        allMenuItems = menuItemDao.getMenuItems();
    }

    public LiveData<DrawerLayoutMenuItem> getMenuOne() {
        return noteDao.getMenuOne();
    }

    public LiveData<DrawerLayoutMenuItem> getMenuTwo() {
        return noteDao.getMenuTwo();
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

    public LiveData<List<DrawerLayoutMenuItem>> getAllTagMenuItems() {
        return noteDao.getTagMenus();
    }

    public LiveData<MergedMenu> getMergedMenuLiveData() {
        return getMergeData();
    }

    public void insertTagMenuItem(DrawerLayoutMenuItem drawerLayoutMenuItem) {
        new InsertMenuItem(menuItemDao).execute(drawerLayoutMenuItem);
    }

    public void updateMenuItem(DrawerLayoutMenuItem drawerLayoutMenuItem) {
        new UpdateMenuItem(menuItemDao).execute(drawerLayoutMenuItem);
    }

    public void deleteMenuItem(DrawerLayoutMenuItem drawerLayoutMenuItem) {
        new DeleteMenuItem(menuItemDao).execute(drawerLayoutMenuItem);
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

    public static class DeleteMenuItem extends AsyncTask<DrawerLayoutMenuItem, Void, Void> {

        private MenuItemDao menuItemDao;

        private DeleteMenuItem(MenuItemDao menuItemDao) {
            this.menuItemDao = menuItemDao;
        }

        @Override
        protected Void doInBackground(DrawerLayoutMenuItem... drawerLayoutMenuItems) {
            this.menuItemDao.delete(drawerLayoutMenuItems[0]);
            return null;
        }
    }
}
