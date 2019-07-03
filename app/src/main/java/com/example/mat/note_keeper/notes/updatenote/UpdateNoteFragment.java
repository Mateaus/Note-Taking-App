package com.example.mat.note_keeper.notes.updatenote;

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

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.mainactivity.entity.Tag;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;
import com.example.mat.note_keeper.mainactivity.ui.MainActivity;
import com.example.mat.note_keeper.notes.note.NoteViewModel;
import com.example.mat.note_keeper.notes.note.entity.Note;

import java.util.ArrayList;
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
        View v = inflater.inflate(R.layout.fragment_update_note, container, false);
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

        this.updateNoteViewModel.getAllTagCategories().observe(this, new Observer<List<TagCategory>>() {
            @Override
            public void onChanged(List<TagCategory> tagCategories) {
                if (tagCategories != null) {
                    List<Tag> tags = new ArrayList<>(tagCategories.get(0).getItems());

                    ArrayAdapter<Tag> tagArrayAdapter = new ArrayAdapter<Tag>(getContext(), android.R.layout.simple_list_item_1, tags);
                    tagArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tagS.setAdapter(tagArrayAdapter);

                    // TODO: clean later
                    String noteTag = getArguments().getString("note_tag");
                    for (Tag tag : tags) {
                        if (tag.getMenuItemName().equals(noteTag)) {
                            tagS.setSelection(tagArrayAdapter.getPosition(tag));
                            break;
                        }
                    }
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
