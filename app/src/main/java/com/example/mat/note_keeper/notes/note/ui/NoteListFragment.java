package com.example.mat.note_keeper.notes.note.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.mainactivity.ui.MainActivity;
import com.example.mat.note_keeper.notes.note.NoteViewFactoryModel;
import com.example.mat.note_keeper.notes.note.NoteViewModel;
import com.example.mat.note_keeper.notes.note.adapter.NoteAdapter;
import com.example.mat.note_keeper.notes.note.entity.Note;
import com.example.mat.note_keeper.notes.updatenote.UpdateNoteFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class NoteListFragment extends Fragment implements OnItemClickListener, OnFavoriteClickListener {

    public static final int ADD_NOTE_REQUEST = 3;
    public static final int UPDATE_NOTE_REQUEST = 4;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.add_note_btn)
    FloatingActionButton addBtn;

    private NoteViewModel noteViewModel;
    private NoteAdapter noteAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note_list, container, false);
        ButterKnife.bind(this, v);
        showBackButton(false);

        eraseCurrentDatabase();
        setUpToolBar();
        setUpNoteAdapter();
        setUpRecyclerView();

        int menuId = Integer.valueOf(getArguments().getString("menu_id"));
        String menuName = getArguments().getString("menu_name");

        this.noteViewModel = ViewModelProviders.of(this, new NoteViewFactoryModel(this.getActivity().getApplication(), menuName)).get(NoteViewModel.class);

        if (menuName.equals("All Notes")) {
            this.noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(@Nullable List<Note> notes) {
                    if (notes != null) {
                        noteAdapter.submitList(notes);
                    }
                }
            });
        } else {
            this.noteViewModel.getNotes().observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(@Nullable List<Note> notes) {
                    if (notes != null) {
                        noteAdapter.submitList(notes);
                    }
                }
            });
        }

        setUpItemTouchHelper();

        return v;
    }

    @OnClick(R.id.add_note_btn)
    public void addButtonHandler() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadAddNoteScreen();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Inserts data sent from AddNoteDialogFragment.
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
/*
            Note addNote = new Note(data.getStringExtra(AddNoteFragment.EXTRA_TAG),
                    data.getStringExtra(AddNoteFragment.EXTRA_TITLE),
                    data.getStringExtra(AddNoteFragment.EXTRA_DESCRIPTION));

            this.noteViewModel.insert(addNote);*/
        }
        // Updates data sent from UpdateNoteFragment.
        if (requestCode == UPDATE_NOTE_REQUEST && resultCode == RESULT_OK) {
            int cid = Integer.valueOf(data.getStringExtra(UpdateNoteFragment.EXTRA_CID));
            int nid = Integer.valueOf(data.getStringExtra(UpdateNoteFragment.EXTRA_NID));
/*
            Note updateNote = new Note(data.getStringExtra(UpdateNoteFragment.EXTRA_TITLE),
                    data.getStringExtra(UpdateNoteFragment.EXTRA_DESCRIPTION));
            updateNote.setC_id(cid);
            updateNote.setN_id(nid);
            this.noteViewModel.update(updateNote);*/
        }
    }

    @Override
    public void onItemClick(Note note) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadUpdateNoteScreen(note);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.note_list_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync_data:
                syncData();
                return true;
            case R.id.settings:
                settings();
                return true;
            default:
                getFragmentManager().popBackStack();
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.note_list);
    }

    private void setUpNoteAdapter() {
        this.noteAdapter = new NoteAdapter(this, this);
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(noteAdapter);
    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(noteViewModel,
                noteAdapter, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void showBackButton(Boolean enable) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.showBackButton(enable);
    }

    private void syncData() {
        // TODO: Sync data from current DB into Firebase auth + realtime DB.
    }

    private void settings() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadColorScreen();
    }

    private void eraseCurrentDatabase() {
        getContext().deleteDatabase("note_database");
    }

    @Override
    public void onFavoriteClick(Note note, int position) {
        System.out.println("Position passed from click:" + position);
        if (note.isNoteFavorite()) {
            note.setNoteFavorite(false);
            note.setNoteTag("default");
            noteAdapter.updateNoteAt(position, false);
        } else {
            note.setNoteFavorite(true);
            note.setNoteTag("Favorites");
            noteAdapter.updateNoteAt(position, true);
        }

        noteViewModel.update(note);
        noteAdapter.notifyItemChanged(position, note);
    }
}

