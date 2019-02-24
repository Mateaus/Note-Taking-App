package com.example.mat.note_keeper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.mat.note_keeper.note_catalogue_app.note_section.addnote.AddNoteFragment;
import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.entity.Catalogue;
import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.ui.CatalogueListFragment;
import com.example.mat.note_keeper.note_catalogue_app.note_section.note.entity.Note;
import com.example.mat.note_keeper.note_catalogue_app.note_section.note.ui.NoteListFragment;
import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.updatecatalogue.UpdateCatalogueFragment;
import com.example.mat.note_keeper.note_catalogue_app.note_section.updatenote.UpdateNoteFragment;
import com.example.mat.note_keeper.settings.SettingFragment;

import static com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.ui.CatalogueListFragment.UPDATE_CATALOGUE_REQUEST;
import static com.example.mat.note_keeper.note_catalogue_app.note_section.note.ui.NoteListFragment.ADD_NOTE_REQUEST;
import static com.example.mat.note_keeper.note_catalogue_app.note_section.note.ui.NoteListFragment.UPDATE_NOTE_REQUEST;

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
            CatalogueListFragment catalogueListFragment = new CatalogueListFragment();
            fragmentManager.beginTransaction().add(R.id.note_container, catalogueListFragment).commit();
        }
    }

    public void loadNoteScreen(Catalogue catalogue) {
        NoteListFragment noteListFragment = new NoteListFragment();

        Bundle bundle = new Bundle();
        bundle.putString("id", Integer.toString(catalogue.getC_id()));
        bundle.putString("name", catalogue.getCsubject());
        bundle.putString("description", catalogue.getCdescription());
        noteListFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.note_container, noteListFragment).addToBackStack(null).commit();
    }

    public void loadUpdateCatalogueScreen(Catalogue catalogue, Fragment fragment) {
        UpdateCatalogueFragment updateCatalogueFragment = new UpdateCatalogueFragment();
        updateCatalogueFragment.setTargetFragment(fragment, UPDATE_CATALOGUE_REQUEST);

        Bundle bundle = new Bundle();
        bundle.putString("cid", Integer.toString(catalogue.getC_id()));
        bundle.putString("csubject", catalogue.getCsubject());
        bundle.putString("cdescription", catalogue.getCdescription());
        updateCatalogueFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.note_container, updateCatalogueFragment).addToBackStack(null).commit();
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
