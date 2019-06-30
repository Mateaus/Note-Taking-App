package com.example.mat.note_keeper.notes.note.ui;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.widget.Toast;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.notes.note.NoteViewModel;
import com.example.mat.note_keeper.notes.note.adapter.NoteAdapter;

import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;
import static androidx.recyclerview.widget.ItemTouchHelper.RIGHT;

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
