package com.example.mat.roomdb_mvvm.note.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mat.roomdb_mvvm.MainActivity;
import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.addnote.AddNoteFragment;
import com.example.mat.roomdb_mvvm.note.NoteViewModel;
import com.example.mat.roomdb_mvvm.note.adapters.NoteAdapter;
import com.example.mat.roomdb_mvvm.note.entity.Note;
import com.example.mat.roomdb_mvvm.updatenote.UpdateNoteFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class NoteListFragment extends Fragment implements OnItemClickListener {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int UPDATE_NOTE_REQUEST = 2;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private NoteViewModel noteViewModel;
    private NoteAdapter noteAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note_list, container, false);
        ButterKnife.bind(this, v);

        setUpToolBar();
        setUpNoteAdapter();
        setUpRecyclerView();

        // getContext().deleteDatabase("note_database");

        this.noteViewModel = ViewModelProviders.of(this.getActivity()).get(NoteViewModel.class);
        this.noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                noteAdapter.submitList(notes);
            }
        });

        setUpItemTouchHelper();

        return v;
    }

    @OnClick(R.id.add_note_btn)
    public void addButtonHandler() {
        AddNoteFragment fragment = new AddNoteFragment();
        fragment.setTargetFragment(NoteListFragment.this, ADD_NOTE_REQUEST);
        fragment.show(getFragmentManager(), "ADD_FRAGMENT");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Inserts data sent from AddNoteFragment.
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            noteViewModel.insert(new Note(data.getStringExtra(AddNoteFragment.EXTRA_TITLE),
                    data.getStringExtra(AddNoteFragment.EXTRA_DESCRIPTION)));
        }
        // Updates data sent from UpdateNoteFragment.
        if (requestCode == UPDATE_NOTE_REQUEST && resultCode == RESULT_OK) {
            noteViewModel.update(new Note(Integer.valueOf(data.getStringExtra(UpdateNoteFragment.EXTRA_ID)),
                    data.getStringExtra(UpdateNoteFragment.EXTRA_TITLE),
                    data.getStringExtra(UpdateNoteFragment.EXTRA_DESCRIPTION)));
        }
    }

    @Override
    public void onItemClick(Note note) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadUpdateNoteScreen(note, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
                return super.onOptionsItemSelected(item);
        }
    }

    private void syncData() {
        // TODO: Sync data from current DB into Firebase auth + realtime DB.
    }

    private void settings() {
        // TODO: Implement a new Activity/Fragment to create a new DB table to handle app color!
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle(R.string.note_list);
    }

    private void setUpNoteAdapter() {
        this.noteAdapter = new NoteAdapter(this);
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
}
