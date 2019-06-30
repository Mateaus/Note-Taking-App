package com.example.mat.note_keeper.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.mainactivity.entity.Tag;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;
import com.example.mat.note_keeper.notes.note.entity.Note;
import com.example.mat.note_keeper.color.entity.Color;
import com.example.mat.note_keeper.color.entity.Theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Database(entities = {TagCategory.class, Note.class, Color.class, Theme.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract TagCategoryDao categoryDao();

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
        private TagCategoryDao tagCategoryDao;
        private NoteDao noteDao;
        private ColorDao colorDao;
        private ThemeDao themeDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            tagCategoryDao = db.categoryDao();
            noteDao = db.noteDao();
            colorDao = db.colorDao();
            themeDao = db.themeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            /*
             * This populates the database when it's first initially out of box.
             */
            Theme mainTheme = new Theme(R.style.BrownThemeOverlay, R.color.brown, R.color.darkbrown,
                    R.color.lightbrown);
            themeDao.insert(mainTheme);

            tagCategoryDao.insert(new TagCategory("Tags",
                    new ArrayList<Tag>(Arrays.asList(
                            new Tag("Default")
                    )))
            );

            for (int i = 0; i < populateThemeColors().size(); i++) {
                colorDao.insert(populateThemeColors().get(i));
            }

            return null;
        }

        private List<Color> populateThemeColors() {
            Color red = new Color("Red Theme", R.style.RedThemeOverlay,
                    R.color.red, R.color.darkred, R.color.lightred);
            Color pink = new Color("Pink Theme", R.style.PinkThemeOverlay,
                    R.color.pink, R.color.darkpink, R.color.lightpink);
            Color purple = new Color("Purple Theme", R.style.PurpleThemeOverlay,
                    R.color.purple, R.color.darkpurple, R.color.lightpurple);
            Color green = new Color("Green Theme", R.style.GreenThemeOverlay,
                    R.color.green, R.color.darkgreen, R.color.lightgreen);
            Color blue = new Color("Blue Theme", R.style.BlueThemeOverlay,
                    R.color.blue, R.color.darkblue, R.color.lightblue);
            Color blueGrey = new Color("Blue Grey Theme", R.style.BlueGreyThemeOverlay,
                    R.color.bluegrey, R.color.darkbluegrey, R.color.lightbluegrey);
            Color indigo = new Color("Indigo Theme", R.style.IndigoThemeOverlay,
                    R.color.indigo, R.color.darkindigo, R.color.lightindigo);
            Color orange = new Color("Orange Theme", R.style.OrangeThemeOverlay,
                    R.color.orange, R.color.darkorange, R.color.lightorange);
            Color yellow = new Color("Yellow Theme", R.style.YellowThemeOverlay,
                    R.color.yellow, R.color.darkyellow, R.color.lightyellow);
            Color brown = new Color("Brown Theme", R.style.BrownThemeOverlay,
                    R.color.brown, R.color.darkbrown, R.color.lightbrown);

            List<Color> colorList = new ArrayList<>(Arrays.asList(
                    red, pink, purple, green, blue, blueGrey, indigo, orange, yellow, brown
            ));

            return colorList;
        }

    }

}
