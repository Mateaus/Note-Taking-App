package com.example.mat.roomdb_mvvm.notes.note.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.color.entity.Theme;
import com.example.mat.roomdb_mvvm.databinding.FragmentNoteListBinding;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;
import com.example.mat.roomdb_mvvm.mainactivity.ui.MainActivity;
import com.example.mat.roomdb_mvvm.notes.note.NoteViewModel;
import com.example.mat.roomdb_mvvm.notes.note.adapter.NoteAdapter;
import com.example.mat.roomdb_mvvm.notes.note.entity.Note;
import com.example.mat.roomdb_mvvm.notes.note.listener.OnFavoriteClickListener;
import com.example.mat.roomdb_mvvm.notes.note.listener.OnItemClickListener;

import java.util.List;

public class NoteListFragment extends Fragment implements OnItemClickListener, OnFavoriteClickListener,
        View.OnClickListener {

    public static final String ALL_NOTES = "All Notes";
    public static final String FAVORITES_NOTES = "Favorites";

    private NoteViewModel noteViewModel;
    private NoteAdapter noteAdapter;

    private FragmentNoteListBinding viewBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_list, container, false);

        setUI();

        String menuName = getArguments().getString("menu_name");
        getActivity().setTitle(menuName);

        this.noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        if (menuName.equals(ALL_NOTES)) {
            this.noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(@Nullable List<Note> notes) {
                    if (notes != null) {
                        noteAdapter.submitList(notes);
                    }
                }
            });
        } else if (menuName.equals(FAVORITES_NOTES)) {
            this.noteViewModel.getAllFavoriteNotes(true).observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    if (notes != null) {
                        noteAdapter.submitList(notes);
                    }
                }
            });
        } else {
            this.noteViewModel.getAllTagNotes(menuName).observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(@Nullable List<Note> notes) {
                    if (notes != null) {
                        noteAdapter.submitList(notes);
                    }
                }
            });
        }

        this.noteViewModel.getTheme().observe(this, new Observer<Theme>() {
            @Override
            public void onChanged(Theme theme) {
                if (theme != null) {
                    viewBinding.fragmentNoteAddBtn.setBackgroundTintList(getResources().getColorStateList(theme.getPrimaryDarkColor()));
                }
            }
        });

        setUpItemTouchHelper();

        return viewBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_note_add_btn:
                addButtonHandler();
                break;
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

    @Override
    public void onFavoriteClick(Note note, int position) {
        if (note.isNoteFavorite()) {
            note.setNoteFavorite(false);
            noteAdapter.updateNoteAt(position, false);
        } else {
            note.setNoteFavorite(true);
            noteAdapter.updateNoteAt(position, true);
        }

        noteViewModel.update(note);
        noteAdapter.notifyItemChanged(position, note);
    }

    private void setUI() {
        showBackButton(false);
        // eraseCurrentDatabase();
        setUpToolBar();
        setUpNoteAdapter();
        setUpRecyclerView();
        viewBinding.fragmentNoteAddBtn.setOnClickListener(this);
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.note_list);
    }

    private void setUpNoteAdapter() {
        this.noteAdapter = new NoteAdapter(this, this);
    }

    private void setUpRecyclerView() {
        viewBinding.fragmentNoteListRv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        viewBinding.fragmentNoteListRv.setAdapter(noteAdapter);
    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(noteViewModel,
                noteAdapter, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(viewBinding.fragmentNoteListRv);
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

    private void addButtonHandler() {
        int menuId = Integer.valueOf(getArguments().getString("menu_id"));
        String menuName = getArguments().getString("menu_name");
        String menuIcon = getArguments().getString("menu_icon");

        int menuNoteSize = 0;
        if (menuId == 1 && menuName.equals("All Notes")) {
            menuNoteSize = noteAdapter.getItemCount();
        } else {
            menuNoteSize = Integer.valueOf(getArguments().getString("menu_size"));
        }

        DrawerMenuItem drawerMenuItem = new DrawerMenuItem(menuId, menuName, menuNoteSize, menuIcon);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadAddNoteScreen(drawerMenuItem);
    }
}

