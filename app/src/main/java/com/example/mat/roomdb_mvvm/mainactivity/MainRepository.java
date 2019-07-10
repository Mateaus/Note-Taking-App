package com.example.mat.roomdb_mvvm.mainactivity;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.mat.roomdb_mvvm.database.MenuItemDao;
import com.example.mat.roomdb_mvvm.database.NoteDao;
import com.example.mat.roomdb_mvvm.database.NoteDatabase;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;
import com.example.mat.roomdb_mvvm.mainactivity.model.MergedMenu;

import java.util.List;

public class MainRepository {

    private MenuItemDao menuItemDao;
    private NoteDao noteDao;

    private MergedMenu mergedMenu = new MergedMenu();

    public MainRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        menuItemDao = noteDatabase.menuItemDao();
        noteDao = noteDatabase.noteDao();
    }

    public LiveData<DrawerMenuItem> getMenuOne() {
        return noteDao.getMenuOne();
    }

    public LiveData<DrawerMenuItem> getMenuTwo() {
        return noteDao.getMenuTwo();
    }

    private MediatorLiveData<MergedMenu> getMergeData() {
        final MediatorLiveData<MergedMenu> mergedMenuMediatorLiveData = new MediatorLiveData<>();
        mergedMenuMediatorLiveData.addSource(getMenuOne(), new Observer<DrawerMenuItem>() {
            @Override
            public void onChanged(DrawerMenuItem drawerMenuItem) {
                mergedMenu.setMenuOne(drawerMenuItem);
                mergedMenuMediatorLiveData.setValue(mergedMenu);
            }
        });

        mergedMenuMediatorLiveData.addSource(getMenuTwo(), new Observer<DrawerMenuItem>() {
            @Override
            public void onChanged(DrawerMenuItem drawerMenuItem) {
                mergedMenu.setMenuTwo(drawerMenuItem);
                mergedMenuMediatorLiveData.setValue(mergedMenu);
            }
        });
        return mergedMenuMediatorLiveData;
    }

    public LiveData<List<DrawerMenuItem>> getAllTagMenuItems() {
        return noteDao.getTagMenus();
    }

    public LiveData<MergedMenu> getMergedMenuLiveData() {
        return getMergeData();
    }

    public void insertTagMenuItem(DrawerMenuItem drawerMenuItem) {
        new InsertMenuItem(menuItemDao).execute(drawerMenuItem);
    }

    public void updateMenuItem(DrawerMenuItem drawerMenuItem) {
        new UpdateMenuItem(menuItemDao).execute(drawerMenuItem);
    }

    public void deleteMenuItem(DrawerMenuItem drawerMenuItem) {
        new DeleteMenuItem(menuItemDao).execute(drawerMenuItem);
    }

    public static class InsertMenuItem extends AsyncTask<DrawerMenuItem, Void, Void> {

        private MenuItemDao menuItemDao;

        public InsertMenuItem(MenuItemDao menuItemDao) {
            this.menuItemDao = menuItemDao;
        }

        @Override
        protected Void doInBackground(DrawerMenuItem... drawerMenuItems) {
            this.menuItemDao.insert(drawerMenuItems[0]);
            return null;
        }
    }

    public static class UpdateMenuItem extends AsyncTask<DrawerMenuItem, Void, Void> {

        private MenuItemDao menuItemDao;

        public UpdateMenuItem(MenuItemDao menuItemDao) {
            this.menuItemDao = menuItemDao;
        }

        @Override
        protected Void doInBackground(DrawerMenuItem... drawerMenuItems) {
            this.menuItemDao.update(drawerMenuItems[0]);
            return null;
        }
    }

    public static class DeleteMenuItem extends AsyncTask<DrawerMenuItem, Void, Void> {

        private MenuItemDao menuItemDao;

        private DeleteMenuItem(MenuItemDao menuItemDao) {
            this.menuItemDao = menuItemDao;
        }

        @Override
        protected Void doInBackground(DrawerMenuItem... drawerMenuItems) {
            this.menuItemDao.delete(drawerMenuItems[0]);
            return null;
        }
    }
}
