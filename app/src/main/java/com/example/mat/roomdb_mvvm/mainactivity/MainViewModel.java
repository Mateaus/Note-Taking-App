package com.example.mat.roomdb_mvvm.mainactivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mat.roomdb_mvvm.color.ColorViewModel;
import com.example.mat.roomdb_mvvm.color.entity.Theme;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;
import com.example.mat.roomdb_mvvm.mainactivity.model.MergedMenu;

import java.util.List;

public class MainViewModel extends ColorViewModel {

    private MainRepository mainRepository;
    private LiveData<MergedMenu> mergedMenu;
    private LiveData<List<DrawerMenuItem>> allTagMenuItems;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.mainRepository = new MainRepository(application);
        this.mergedMenu = mainRepository.getMergedMenuLiveData();
        this.allTagMenuItems = mainRepository.getAllTagMenuItems();
    }

    @Override
    public LiveData<Theme> getTheme() {
        return super.getTheme();
    }

    public LiveData<MergedMenu> getMergedMenuLiveData() {
        return mergedMenu;
    }

    public LiveData<List<DrawerMenuItem>> getAllTagMenuItems() {
        return allTagMenuItems;
    }

    public void insertTagMenuItem(DrawerMenuItem drawerMenuItem) {
        mainRepository.insertTagMenuItem(drawerMenuItem);
    }

    public void updateMenuItem(DrawerMenuItem drawerMenuItem) {
        mainRepository.updateMenuItem(drawerMenuItem);
    }

    public void deleteMenuItem(DrawerMenuItem drawerMenuItem) {
        mainRepository.deleteMenuItem(drawerMenuItem);
    }
}


