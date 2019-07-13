package com.example.mat.roomdb_mvvm.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.color.entity.Color;
import com.example.mat.roomdb_mvvm.color.entity.Theme;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;
import com.example.mat.roomdb_mvvm.notes.note.entity.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Database(entities = {DrawerMenuItem.class, Note.class, Color.class, Theme.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract MenuItemDao menuItemDao();

    public abstract NoteDao noteDao();

    public abstract ColorDao colorDao();

    public abstract ThemeDao themeDao();

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
        private MenuItemDao menuItemDao;
        private NoteDao noteDao;
        private ColorDao colorDao;
        private ThemeDao themeDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            menuItemDao = db.menuItemDao();
            noteDao = db.noteDao();
            colorDao = db.colorDao();
            themeDao = db.themeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            menuItemDao.insert(new DrawerMenuItem("All Notes", 0, "note_icon"));
            menuItemDao.insert(new DrawerMenuItem("Favorites", 0, "favorite_star_icon"));
            menuItemDao.insert(new DrawerMenuItem("Not Tagged", 0, "tag_border_icon"));


            /*
             * This populates the database when it's first initially out of box.
             */
            Theme mainTheme = new Theme(R.style.AppTheme, R.color.themePrimary, R.color.themePrimaryDark,
                    R.color.themeAccent);
            themeDao.insert(mainTheme);

            for (int i = 0; i < populateThemeColors().size(); i++) {
                colorDao.insert(populateThemeColors().get(i));
            }

            return null;
        }

        private List<Color> populateThemeColors() {
            Color blueGrey = new Color("Blue Grey Theme", R.style.BlueGreyThemeOverlay,
                    R.color.bluegrey, R.color.darkbluegrey, R.color.lightbluegrey);
            Color darkTheme = new Color("Dark Theme", R.style.AppTheme,
                    R.color.themePrimary, R.color.themePrimaryDark, R.color.themeAccent);
            Color dTheme = new Color("Discord Theme", R.style.DsThemeOverlay,
                    R.color.dPrimary, R.color.dPrimaryDark, R.color.dAccent);
            Color limeTheme = new Color("Brown Theme", R.style.BrownThemeOverlay,
                    R.color.brownPrimary, R.color.brownPrimaryDark, R.color.brownAccent);

            List<Color> colorList = new ArrayList<>(Arrays.asList(
                    dTheme, blueGrey, darkTheme, limeTheme
            ));

            return colorList;
        }

    }

}
