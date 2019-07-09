package com.example.mat.roomdb_mvvm.mainactivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mat.roomdb_mvvm.color.ColorViewModel;
import com.example.mat.roomdb_mvvm.color.entity.Theme;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerLayoutMenuItem;
import com.example.mat.roomdb_mvvm.mainactivity.model.MergedMenu;

import java.util.List;

public class MainViewModel extends ColorViewModel {

    private MainRepository mainRepository;
    private LiveData<MergedMenu> mergedMenu;
    private LiveData<List<DrawerLayoutMenuItem>> allTagMenuItems;

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

    public LiveData<List<DrawerLayoutMenuItem>> getAllTagMenuItems() {
        return allTagMenuItems;
    }

    public void insertTagMenuItem(DrawerLayoutMenuItem drawerLayoutMenuItem) {
        mainRepository.insertTagMenuItem(drawerLayoutMenuItem);
    }

    public void updateMenuItem(DrawerLayoutMenuItem drawerLayoutMenuItem) {
        mainRepository.updateMenuItem(drawerLayoutMenuItem);
    }

    public void deleteMenuItem(DrawerLayoutMenuItem drawerLayoutMenuItem) {
        mainRepository.deleteMenuItem(drawerLayoutMenuItem);
    }
}


