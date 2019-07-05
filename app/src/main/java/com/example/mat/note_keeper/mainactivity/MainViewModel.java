package com.example.mat.note_keeper.mainactivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mat.note_keeper.color.ColorViewModel;
import com.example.mat.note_keeper.color.entity.Color;
import com.example.mat.note_keeper.color.entity.Theme;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;
import com.example.mat.note_keeper.mainactivity.model.DrawerLayoutMenuItem;
import com.example.mat.note_keeper.mainactivity.model.MergedMenu;

import java.util.List;

public class MainViewModel extends ColorViewModel {

    private MainRepository mainRepository;
    private LiveData<List<DrawerLayoutMenuItem>> allMenuItems;
    private LiveData<List<DrawerLayoutMenuItem>> allTagMenuItems;
    private LiveData<Integer> allNotesSize;
    private LiveData<List<TagCategory>> allTagCategories;

    private LiveData<Integer> allNoteSize;
    private LiveData<Integer> allFavoriteNoteSize;
    private LiveData<List<Integer>> allTagNotesSizeList;

    private LiveData<MergedMenu> mergedMenu;
    private MergedMenu mergedData = new MergedMenu();

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.mainRepository = new MainRepository(application);
        this.allMenuItems = mainRepository.getAllMenuItems();
        this.allTagMenuItems = mainRepository.getAllTagMenuItems();
        this.allNotesSize = mainRepository.getAllNotesSize();
        this.allTagCategories = mainRepository.getAllTagCategories();

        allNoteSize = mainRepository.getAllNoteSize();
        allFavoriteNoteSize = mainRepository.getAllFavoriteNoteSize();
        allTagNotesSizeList = mainRepository.getAllTagNotesSizeList();

        mergedMenu = mainRepository.getMergedMenuLiveData();
    }

    public LiveData<DrawerLayoutMenuItem> getMenuTuple() {
        return mainRepository.getMenuOne();
    }

    public LiveData<MergedMenu> getMergedMenuLiveData() {
        return mergedMenu;
    }

    public LiveData<List<DrawerLayoutMenuItem>> getTagMenus() {
        return mainRepository.getTagMenus();
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

    public LiveData<List<DrawerLayoutMenuItem>> getAllMenuItems() {
        return allMenuItems;
    }

    public LiveData<List<DrawerLayoutMenuItem>> getAllTagMenuItems() {
        return allTagMenuItems;
    }

    public void insertTagMenuItem(DrawerLayoutMenuItem drawerLayoutMenuItem) {
        mainRepository.insertTagMenuItem(drawerLayoutMenuItem);
    }

    public void updateTagCategory(TagCategory tagCategory) {
        mainRepository.updateTagCategory(tagCategory);
    }

    public void updateMenuItem(DrawerLayoutMenuItem drawerLayoutMenuItem) {
        mainRepository.updateMenuItem(drawerLayoutMenuItem);
    }

    public LiveData<Integer> getAllNotesSize() {
        return allNotesSize;
    }

    public LiveData<List<TagCategory>> getAllTagCategories() {
        return allTagCategories;
    }
}


