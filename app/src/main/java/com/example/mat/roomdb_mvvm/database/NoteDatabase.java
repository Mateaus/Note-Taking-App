package com.example.mat.roomdb_mvvm.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.mat.roomdb_mvvm.note.entity.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

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
        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*
             * This populates the database when it's first initially out of box.
             */
            Note note1 = new Note("Example Title 1", "Example description . . .");
            note1.setDate(getCurrentDate());

            Note note2 = new Note("Example Title 2", "Example description . . .");
            note2.setDate(getCurrentDate());

            Note note3 = new Note("Example Title 3", "Example description . . .");
            note3.setDate(getCurrentDate());

            Note note4 = new Note("Example Title 4", "Example description . . .");
            note4.setDate(getCurrentDate());

            Note note5 = new Note("Example Title 5", "Example description . . .");
            note5.setDate(getCurrentDate());

            Note note6 = new Note("Example Title 6", "Example description . . .");
            note6.setDate(getCurrentDate());

            Note note7 = new Note("Example Title 7", "Example description . . .");
            note7.setDate(getCurrentDate());

            Note note8 = new Note("Example Title 8", "Example description . . .");
            note8.setDate(getCurrentDate());

            Note note9 = new Note("Example Title 9", "Example description . . .");
            note9.setDate(getCurrentDate());

            Note note10 = new Note("Example Title 10", "Example description . . .");
            note10.setDate(getCurrentDate());

            noteDao.insert(note1);
            noteDao.insert(note2);
            noteDao.insert(note3);
            noteDao.insert(note4);
            noteDao.insert(note5);
            noteDao.insert(note6);
            noteDao.insert(note7);
            noteDao.insert(note8);
            noteDao.insert(note9);
            noteDao.insert(note10);
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
