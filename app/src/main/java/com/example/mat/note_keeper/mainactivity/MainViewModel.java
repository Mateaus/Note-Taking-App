package com.example.mat.note_keeper.mainactivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mat.note_keeper.color.ColorViewModel;
import com.example.mat.note_keeper.color.entity.Color;
import com.example.mat.note_keeper.color.entity.Theme;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;
import com.example.mat.note_keeper.mainactivity.model.MenuItem;

import java.util.List;

public class MainViewModel extends ColorViewModel {

    private MainRepository mainRepository;
    private LiveData<List<MenuItem>> allMenuItems;
    private LiveData<Integer> allNotesSize;
    private LiveData<List<TagCategory>> allTagCategories;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.mainRepository = new MainRepository(application);
        this.allMenuItems = mainRepository.getAllMenuItems();
        this.allNotesSize = mainRepository.getAllNotesSize();
        this.allTagCategories = mainRepository.getAllTagCategories();
    }

    @Override
    public void insertColor(Color color) {
        super.insertColor(color);
    }

    @Override
    public void updateColor(Color color) {
        super.updateColor(color);
    }

    @Override
    public void updateTheme(Theme theme) {
        super.updateTheme(theme);
    }

    @Override
    public LiveData<List<Color>> getAllColors() {
        return super.getAllColors();
    }

    @Override
    public LiveData<Theme> getTheme() {
        return super.getTheme();
    }

    public LiveData<List<MenuItem>> getAllMenuItems() {
        return allMenuItems;
    }

    public void updateMenuItem(MenuItem menuItem) {
        mainRepository.updateMenuItem(menuItem);
    }

    public LiveData<Integer> getAllNotesSize() {
        return allNotesSize;
    }

    public LiveData<List<TagCategory>> getAllTagCategories() {
        return allTagCategories;
    }
}


