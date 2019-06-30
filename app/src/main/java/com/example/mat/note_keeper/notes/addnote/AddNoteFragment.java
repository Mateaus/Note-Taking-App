package com.example.mat.note_keeper.notes.addnote;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mat.note_keeper.mainactivity.ui.MainActivity;
import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.notes.note.entity.Note;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNoteFragment extends Fragment {

    public static final String EXTRA_TAG =
            "com.example.mat.roomdb_mvvm.EXTRA_TAG";

    public static final String EXTRA_TITLE =
            "com.example.mat.roomdb_mvvm.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.example.mat.roomdb_mvvm.EXTRA_DESCRIPTION";

    @BindView(R.id.tagET)
    EditText tagEt;
    @BindView(R.id.titleET)
    EditText titleET;
    @BindView(R.id.descriptionET)
    EditText descriptionET;

    private AddNoteViewModel addNoteViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_note, container, false);
        ButterKnife.bind(this, v);
        showBackButton(true);

        this.addNoteViewModel = ViewModelProviders.of(this).get(AddNoteViewModel.class);
        this.addNoteViewModel.getMessage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer message) {
                if (message != null) {
                    if (message == R.string.note_add) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                    getFragmentManager().popBackStack();
                }
            }
        });

        setUpToolBar();

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
                addNote();
                return true;
            default:
                getFragmentManager().popBackStack();
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.note_add);
    }

    private void addNote() {
        this.addNoteViewModel.addNote(new Note(tagEt.getText().toString(),
                titleET.getText().toString(), descriptionET.getText().toString()));
    }

    private void showBackButton(Boolean enable) {
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.showBackButton(enable);
    }
}
