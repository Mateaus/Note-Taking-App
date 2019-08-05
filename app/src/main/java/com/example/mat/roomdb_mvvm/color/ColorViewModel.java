package com.example.mat.roomdb_mvvm.color;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.example.mat.roomdb_mvvm.color.entity.Color;
import com.example.mat.roomdb_mvvm.color.entity.Theme;

import java.util.List;

public class ColorViewModel extends AndroidViewModel {

    private ColorRepository colorRepository;
    private LiveData<List<Color>> allColors;
    private LiveData<Theme> theme;

    public ColorViewModel(@NonNull Application application) {
        super(application);
        this.colorRepository = new ColorRepository(application);
        this.allColors = colorRepository.getAllColors();
        this.theme = colorRepository.getTheme();
    }

    public void insertColor(Color color) {
        colorRepository.insertColor(color);
    }

    public void updateColor(Color color) {
        colorRepository.updateColor(color);
    }

    public void updateTheme(Theme theme) {
        theme.setId(1);
        colorRepository.updateTheme(theme);
    }

    public void updateThemeMode(boolean isDarkMode) {
        colorRepository.updateThemeMode(isDarkMode);
    }

    public LiveData<List<Color>> getAllColors() {
        return allColors;
    }

    public LiveData<Theme> getTheme() {
        return theme;
    }
}
