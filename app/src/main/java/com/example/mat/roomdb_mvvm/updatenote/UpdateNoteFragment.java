package com.example.mat.roomdb_mvvm.updatenote;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.note.entity.Note;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateNoteFragment extends Fragment {

    public static final String EXTRA_ID =
            "com.example.mat.roomdb_mvvm.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.example.mat.roomdb_mvvm.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.example.mat.roomdb_mvvm.EXTRA_DESCRIPTION";

    @BindView(R.id.titleET)
    EditText titleET;
    @BindView(R.id.descriptionET)
    EditText descriptionET;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private UpdateNoteViewModel updateNoteViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_note, container, false);
        ButterKnife.bind(this, v);

        setUpToolBar();
        setupNote();

        this.updateNoteViewModel = ViewModelProviders.of(this).get(UpdateNoteViewModel.class);
        this.updateNoteViewModel.getMessage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getActivity().setTitle(R.string.note_update);
    }

    private void setupNote() {
        String category = getArguments().getString("title");
        String description = getArguments().getString("description");

        titleET.setText(category);
        descriptionET.setText(description);
    }

    private void updateNote() {
        updateNoteViewModel.updateNote(new Note(titleET.getText().toString(),
                descriptionET.getText().toString()), this);
    }
}
