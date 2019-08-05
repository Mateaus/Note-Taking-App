package com.example.mat.roomdb_mvvm.color;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.mat.roomdb_mvvm.color.entity.Color;
import com.example.mat.roomdb_mvvm.color.entity.Theme;
import com.example.mat.roomdb_mvvm.database.ColorDao;
import com.example.mat.roomdb_mvvm.database.NoteDatabase;
import com.example.mat.roomdb_mvvm.database.ThemeDao;

import java.util.List;

public class ColorRepository {

    private ColorDao colorDao;
    private ThemeDao themeDao;

    private LiveData<List<Color>> allColors;
    private LiveData<Theme> theme;

    public ColorRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        colorDao = database.colorDao();
        themeDao = database.themeDao();
        allColors = colorDao.getAllColors();
        theme = themeDao.getTheme();
    }

    public void insertColor(Color color) {
        new InsertColorAsyncTask(colorDao).execute(color);
    }

    public void updateColor(Color color) {
        new UpdateColorAsyncTask(colorDao).execute(color);
    }

    public void updateTheme(Theme theme) {
        new UpdateThemeAsyncTask(themeDao).execute(theme);
    }

    public void updateThemeMode(boolean isDarkMode) {
        new UpdateThemeModeAsyncTask(themeDao).execute(isDarkMode);
    }

    public LiveData<List<Color>> getAllColors() {
        return allColors;
    }

    public LiveData<Theme> getTheme() {
        return theme;
    }

    public static class InsertColorAsyncTask extends AsyncTask<Color, Void, Void> {

        private ColorDao colorDao;

        private InsertColorAsyncTask(ColorDao colorDao) {
            this.colorDao = colorDao;
        }

        @Override
        protected Void doInBackground(Color... colors) {
            this.colorDao.insert(colors[0]);
            return null;
        }
    }

    public static class UpdateColorAsyncTask extends AsyncTask<Color, Void, Void> {

        private ColorDao colorDao;

        private UpdateColorAsyncTask(ColorDao colorDao) {
            this.colorDao = colorDao;
        }

        @Override
        protected Void doInBackground(Color... colors) {
            this.colorDao.update(colors[0]);
            return null;
        }
    }

    public static class UpdateThemeAsyncTask extends AsyncTask<Theme, Void, Void> {

        private ThemeDao themeDao;

        private UpdateThemeAsyncTask(ThemeDao themeDao) {
            this.themeDao = themeDao;
        }

        @Override
        protected Void doInBackground(Theme... themes) {
            this.themeDao.update(themes[0]);
            return null;
        }
    }

    public static class UpdateThemeModeAsyncTask extends AsyncTask<Boolean, Void, Void> {

        private ThemeDao themeDao;

        private UpdateThemeModeAsyncTask(ThemeDao themeDao) {
            this.themeDao = themeDao;
        }

        @Override
        protected Void doInBackground(Boolean... booleans) {
            this.themeDao.updateMode(booleans[0]);
            return null;
        }
    }
}
