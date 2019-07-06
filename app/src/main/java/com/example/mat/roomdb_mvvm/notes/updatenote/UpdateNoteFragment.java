package com.example.mat.roomdb_mvvm.notes.updatenote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerLayoutMenuItem;
import com.example.mat.roomdb_mvvm.mainactivity.ui.MainActivity;
import com.example.mat.roomdb_mvvm.notes.note.NoteViewModel;
import com.example.mat.roomdb_mvvm.notes.note.entity.Note;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateNoteFragment extends Fragment {
    public static final String EXTRA_CID =
            "com.example.mat.roomdb_mvvm.EXTRA_CID";

    public static final String EXTRA_NID =
            "com.example.mat.roomdb_mvvm.EXTRA_NID";

    public static final String EXTRA_TITLE =
            "com.example.mat.roomdb_mvvm.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.example.mat.roomdb_mvvm.EXTRA_DESCRIPTION";

    @BindView(R.id.titleET)
    EditText titleET;

    @BindView(R.id.descriptionET)
    EditText descriptionET;

    @BindView(R.id.tagS)
    Spinner tagS;

    private UpdateNoteViewModel updateNoteViewModel;
    private NoteViewModel noteViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_note, container, false);
        ButterKnife.bind(this, v);
        showBackButton(true);

        setUpToolBar();

        this.updateNoteViewModel = ViewModelProviders.of(this).get(UpdateNoteViewModel.class);
        this.updateNoteViewModel.getMessage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer message) {
                if (message != null) {
                    if (message == R.string.note_updated) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                    getFragmentManager().popBackStack();
                }
            }
        });

        this.updateNoteViewModel.getAllTagMenuItems().observe(this, new Observer<List<DrawerLayoutMenuItem>>() {
            @Override
            public void onChanged(List<DrawerLayoutMenuItem> drawerLayoutMenuItems) {
                if (drawerLayoutMenuItems != null && drawerLayoutMenuItems.size() != 0) {
                    Collections.reverse(drawerLayoutMenuItems);
                    ArrayAdapter<DrawerLayoutMenuItem> tagArrayAdapter = new ArrayAdapter<DrawerLayoutMenuItem>(getContext(), R.layout.spinner_item_text, drawerLayoutMenuItems);
                    tagArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tagS.setAdapter(tagArrayAdapter);
                }
            }
        });

        setupNote();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.update_note_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_note:
                updateNote();
                return true;
            default:
                getFragmentManager().popBackStack();
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.note_update);
    }

    private void setupNote() {
        String noteTag = getArguments().getString("note_tag");
        String noteTitle = getArguments().getString("note_title");
        String noteDescription = getArguments().getString("note_description");

        titleET.setText(noteTitle);
        descriptionET.setText(noteDescription);
    }

    private void updateNote() {
        String noteId = getArguments().getString("note_id");
        String noteTag = getArguments().getString("note_tag");
        String noteTitle = getArguments().getString("note_title");
        String noteDescription = getArguments().getString("note_description");

        Note oldNote = new Note(noteTag, noteTitle, noteDescription);
        oldNote.setNoteId(Integer.valueOf(noteId));

        Note newNote = new Note(tagS.getSelectedItem().toString(),
                titleET.getText().toString(), descriptionET.getText().toString());

        this.updateNoteViewModel.updateNote(oldNote, newNote);
    }

    private void showBackButton(Boolean enable) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.showBackButton(enable);
    }
}
