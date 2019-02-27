package com.example.mat.note_keeper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.mat.note_keeper.category_note_app.category_section.category.ui.CategoryListFragment;
import com.example.mat.note_keeper.category_note_app.category_section.updatecategory.UpdateCategoryFragment;
import com.example.mat.note_keeper.category_note_app.note_section.addnote.AddNoteFragment;
import com.example.mat.note_keeper.category_note_app.category_section.category.entity.Category;
import com.example.mat.note_keeper.category_note_app.note_section.note.entity.Note;
import com.example.mat.note_keeper.category_note_app.note_section.note.ui.NoteListFragment;
import com.example.mat.note_keeper.category_note_app.note_section.updatenote.UpdateNoteFragment;
import com.example.mat.note_keeper.settings.SettingFragment;

import static com.example.mat.note_keeper.category_note_app.category_section.category.ui.CategoryListFragment.UPDATE_CATEGORY_REQUEST;
import static com.example.mat.note_keeper.category_note_app.note_section.note.ui.NoteListFragment.ADD_NOTE_REQUEST;
import static com.example.mat.note_keeper.category_note_app.note_section.note.ui.NoteListFragment.UPDATE_NOTE_REQUEST;

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
            CategoryListFragment categoryListFragment = new CategoryListFragment();
            fragmentManager.beginTransaction().add(R.id.note_container, categoryListFragment).commit();
        }
    }

    public void loadNoteScreen(Category category) {
        NoteListFragment noteListFragment = new NoteListFragment();

        Bundle bundle = new Bundle();
        bundle.putString("id", Integer.toString(category.getC_id()));
        bundle.putString("name", category.getCsubject());
        bundle.putString("description", category.getCdescription());
        noteListFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.note_container, noteListFragment).addToBackStack(null).commit();
    }

    public void loadUpdateCategoryScreen(Category category, Fragment fragment) {
        UpdateCategoryFragment updateCategoryFragment = new UpdateCategoryFragment();
        updateCategoryFragment.setTargetFragment(fragment, UPDATE_CATEGORY_REQUEST);

        Bundle bundle = new Bundle();
        bundle.putString("cid", Integer.toString(category.getC_id()));
        bundle.putString("csubject", category.getCsubject());
        bundle.putString("cdescription", category.getCdescription());
        updateCategoryFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.note_container, updateCategoryFragment).addToBackStack(null).commit();
    }

    public void loadAddNoteScreen(Fragment fragment) {
        AddNoteFragment addNoteFragment = new AddNoteFragment();
        addNoteFragment.setTargetFragment(fragment, ADD_NOTE_REQUEST);

        fragmentManager.beginTransaction()
                .replace(R.id.note_container, addNoteFragment).addToBackStack(null).commit();
    }

    public void loadUpdateNoteScreen(Note note, Fragment fragment) {
        UpdateNoteFragment updateNoteFragment = new UpdateNoteFragment();
        updateNoteFragment.setTargetFragment(fragment, UPDATE_NOTE_REQUEST);

        Bundle bundle = new Bundle();
        bundle.putString("cid", Integer.toString(note.getC_id()));
        bundle.putString("nid", Integer.toString(note.getN_id()));
        bundle.putString("title", note.getNtitle());
        bundle.putString("description", note.getNdescription());
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
