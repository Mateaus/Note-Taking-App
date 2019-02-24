package com.example.mat.note_keeper.color;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.mat.note_keeper.color.entity.Color;
import com.example.mat.note_keeper.database.ColorDao;
import com.example.mat.note_keeper.database.NoteDatabase;

public class ColorRepository {

    private ColorDao colorDao;

    private LiveData<Color> selectedColors;

    public ColorRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        colorDao = database.colorDao();
        selectedColors = colorDao.getSelectedColors();
    }

    public void update(Color color) {
        color.setId(1);
        new UpdateColorAsyncTask(colorDao).execute(color);
    }

    public LiveData<Color> getSelectedColors() {
        return selectedColors;
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
}
