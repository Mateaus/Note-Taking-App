package com.example.mat.roomdb_mvvm.notes.note.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
import com.example.mat.roomdb_mvvm.fragmentbasecallback.BaseFragmentListener;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;
import com.example.mat.roomdb_mvvm.notes.note.NoteViewModel;
import com.example.mat.roomdb_mvvm.notes.note.adapter.NoteAdapter;
import com.example.mat.roomdb_mvvm.notes.note.entity.Note;
import com.example.mat.roomdb_mvvm.notes.note.listener.OnFavoriteClickListener;
import com.example.mat.roomdb_mvvm.notes.note.listener.OnItemClickListener;

import java.util.List;

public class NoteListFragment extends Fragment implements OnItemClickListener,
        OnFavoriteClickListener, View.OnClickListener {

    public static final String ALL_NOTES = "All Notes";
    public static final String FAVORITES_NOTES = "Favorites";

    private Theme theme;
    private int menuId;
    private String menuName;
    private String menuIcon;
    private int menuSize;

    private NoteViewModel noteViewModel;
    private NoteAdapter noteAdapter;
    private NoteListFragmentListener listener;
    private FragmentNoteListBinding viewBinding;

    public interface NoteListFragmentListener extends BaseFragmentListener {
        void onNoteAddButtonClick(DrawerMenuItem drawerMenuItem);

        void onNoteCardViewClick(Note note);

        void onColorToolbarIconClick();
    }

    public static NoteListFragment newStartUpInstance() {
        NoteListFragment noteListFragment = new NoteListFragment();

        Bundle bundle = new Bundle();
        bundle.putString("menu_id", "1");
        bundle.putString("menu_name", "All Notes");
        bundle.putString("menu_icon", "note_icon");
        noteListFragment.setArguments(bundle);

        return noteListFragment;
    }

    public static NoteListFragment newOnMenuSelectInstance(DrawerMenuItem drawerMenuItem) {
        NoteListFragment noteListFragment = new NoteListFragment();

        Bundle bundle = new Bundle();
        bundle.putString("menu_id", String.valueOf(drawerMenuItem.getMenuItemId()));
        bundle.putString("menu_name", drawerMenuItem.getMenuItemName());
        bundle.putString("menu_size", String.valueOf(drawerMenuItem.getMenuItemSize()));
        bundle.putString("menu_icon", drawerMenuItem.getMenuItemImage());
        noteListFragment.setArguments(bundle);

        return noteListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (NoteListFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoteListFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String menuStringId = getArguments() != null ? getArguments().getString("menu_id") : null;
        String menuStringSize = getArguments() != null ? getArguments().getString("menu_size") : null;
        menuId = menuStringId != null ? Integer.valueOf(menuStringId) : 0;
        menuName = getArguments() != null ? getArguments().getString("menu_name") : "";
        menuIcon = getArguments() != null ? getArguments().getString("menu_icon") : "";
        menuSize = menuStringSize != null ? Integer.valueOf(menuStringSize) : 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_list, container, false);

        setUI();

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
        listener.onNoteCardViewClick(note);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.note_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.themes:
                listener.onColorToolbarIconClick();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
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

    @Override
    public void onResume() {
        super.onResume();
        if (theme != null) {
            viewBinding.fragmentNoteAddBtn.setBackgroundTintList(
                    getResources().getColorStateList(theme.getPrimaryDarkColor()));
            noteAdapter.setTheme(theme);
            noteAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 50) {
            theme = data.getParcelableExtra("Warrior");
        }
    }

    private void setUI() {
        listener.setToolbarTitle(menuName);
        listener.setBackButtonVisible(false);
        viewBinding.fragmentNoteAddBtn.setOnClickListener(this);
        setUpToolBar();
        setUpNoteAdapter();
        setUpRecyclerView();
        setUpLiveData();
        setUpItemTouchHelper();
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
    }

    private void setUpNoteAdapter() {
        this.noteAdapter = new NoteAdapter(this, this);
    }

    private void setUpRecyclerView() {
        viewBinding.fragmentNoteListRv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        viewBinding.fragmentNoteListRv.setAdapter(noteAdapter);
    }

    private void setUpLiveData() {
        this.noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        if (menuName.equals(ALL_NOTES)) {
            this.noteViewModel.getAllNotesById().observe(this, new Observer<List<Note>>() {
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
                    viewBinding.fragmentNoteAddBtn.setBackgroundTintList(
                            getResources().getColorStateList(theme.getPrimaryDarkColor()));
                    noteAdapter.setTheme(theme);
                    noteAdapter.notifyDataSetChanged();
                    // Only needed to listen once when app is opened.
                    noteViewModel.getTheme().removeObserver(this);
                }
            }
        });
    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(noteViewModel,
                noteAdapter, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(viewBinding.fragmentNoteListRv);
    }

    private void addButtonHandler() {
        int menuNoteSize = menuId == 1 && menuName.equals("All Notes") ?
                noteAdapter.getItemCount() : menuSize;
        listener.onNoteAddButtonClick(new DrawerMenuItem(menuId, menuName, menuNoteSize, menuIcon));
    }
}

