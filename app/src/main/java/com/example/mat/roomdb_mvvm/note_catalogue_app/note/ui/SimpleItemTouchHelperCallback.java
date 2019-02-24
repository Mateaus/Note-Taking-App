package com.example.mat.roomdb_mvvm.note_catalogue_app.note.ui;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.note_catalogue_app.note.NoteViewModel;
import com.example.mat.roomdb_mvvm.note_catalogue_app.note.adapters.NoteAdapter;

import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private NoteViewModel noteViewModel;
    private NoteAdapter noteAdapter;
    private Fragment fragment;

    public SimpleItemTouchHelperCallback(NoteViewModel noteViewModel,
                                         NoteAdapter noteAdapter, Fragment fragment) {
        this.noteViewModel = noteViewModel;
        this.noteAdapter = noteAdapter;
        this.fragment = fragment;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT | RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
        Toast.makeText(fragment.getActivity(), R.string.note_deleted, Toast.LENGTH_SHORT).show();
    }
}
