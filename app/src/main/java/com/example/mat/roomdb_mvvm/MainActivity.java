package com.example.mat.roomdb_mvvm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.mat.roomdb_mvvm.note.entity.Note;
import com.example.mat.roomdb_mvvm.note.ui.NoteListFragment;
import com.example.mat.roomdb_mvvm.settings.SettingFragment;
import com.example.mat.roomdb_mvvm.updatenote.UpdateNoteFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        Fragment mainFragment = (Fragment) fragmentManager.findFragmentById(R.id.note_container);

        if (mainFragment == null) {
            NoteListFragment noteListFragment = new NoteListFragment();
            fragmentManager.beginTransaction().add(R.id.note_container, noteListFragment).commit();
        }
    }

    public void loadUpdateNoteScreen(Note note, Fragment fragment) {
        UpdateNoteFragment updateNoteFragment = new UpdateNoteFragment();
        updateNoteFragment.setTargetFragment(fragment, NoteListFragment.UPDATE_NOTE_REQUEST);

        Bundle bundle = new Bundle();
        bundle.putString("id", Integer.toString(note.getId()));
        bundle.putString("title", note.getTitle());
        bundle.putString("description", note.getDescription());
        updateNoteFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.note_container, updateNoteFragment).addToBackStack(null).commit();
    }

    public void loadSettingScreen() {
        SettingFragment settingFragment = new SettingFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.note_container, settingFragment).addToBackStack(null).commit();
    }
}
