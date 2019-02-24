package com.example.mat.note_keeper.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.mat.note_keeper.color.entity.Color;
import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.entity.Catalogue;
import com.example.mat.note_keeper.note_catalogue_app.note_section.note.entity.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Database(entities = {Catalogue.class, Note.class, Color.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract CatalogueDao catalogueDao();
    public abstract NoteDao noteDao();
    public abstract ColorDao colorDao();

    // synchronized, only one thread at a time can access this method.
    // This way you don't accidentally create two instances of this database
    // when two different threads try to access this method at the same time
    // because this can happen in a multi thread environment.
    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private CatalogueDao catalogueDao;
        private NoteDao noteDao;
        private ColorDao colorDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            catalogueDao = db.catalogueDao();
            noteDao = db.noteDao();
            colorDao = db.colorDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            /*
             * This populates the database when it's first initially out of box.
             */
            Catalogue catalogue = new Catalogue("Test 1", "This is successful test!");
            catalogueDao.insert(catalogue);
/*
            int darkbrown = android.graphics.Color.parseColor("#4b2c20");
            int brown = android.graphics.Color.parseColor("#795548");
            int lightbrown = android.graphics.Color.parseColor("#a98274");
            int white = android.graphics.Color.parseColor("#ffffff");
            int grey = android.graphics.Color.parseColor("#757575");

            colorDao.insert(new Color(darkbrown, lightbrown,
                    white, white, white, grey, grey, grey,
                    white, white, brown));

            for (int i = 1; i <= 10; i++) {
                Note note = new Note("Example Title " + i, "Example description . . .");
                note.setNdate(getCurrentDate());
                noteDao.insert(note);
            }*/

            return null;
        }

        private String getCurrentDate() {
            DateFormat hourFormat = new SimpleDateFormat("h:mm a");
            DateFormat dateFormat = new SimpleDateFormat("M/d/y");
            String time = hourFormat.format(Calendar.getInstance().getTime());
            String date = dateFormat.format(Calendar.getInstance().getTime());
            String calendar = "\t" + time + "\n" + date;
            return calendar;
        }
    }

}
