package com.example.mat.roomdb_mvvm.note.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.view.Window;
import android.view.WindowManager;

import com.example.mat.roomdb_mvvm.MainActivity;
import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.addnote.AddNoteFragmentDialogFragment;
import com.example.mat.roomdb_mvvm.note.NoteViewModel;
import com.example.mat.roomdb_mvvm.note.adapters.NoteAdapter;
import com.example.mat.roomdb_mvvm.color.entity.Color;
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
    @BindView(R.id.add_note_btn)
    FloatingActionButton addBtn;

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

        this.noteViewModel.getSelectedColor().observe(this, new Observer<Color>() {
            @Override
            public void onChanged(@Nullable Color color) {
                if (color != null) {
                    setUpStatusBar(getResources().getColor(color.getStatusBarColor()));
                    addBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(color.getAddButtonBackgroundColor())));
                    addBtn.setColorFilter(getResources().getColor(color.getAddButtonIconColor()));
                    toolbar.setBackgroundColor(getResources().getColor(color.getToolBarColor()));
                    toolbar.setTitleTextColor(getResources().getColor(color.getToolBarTitleColor()));
                    recyclerView.setBackgroundColor(getResources().getColor(color.getBodyBackgroundColor()));
                }
            }
        });

        setUpItemTouchHelper();

        return v;
    }

    @OnClick(R.id.add_note_btn)
    public void addButtonHandler() {
        AddNoteFragmentDialogFragment fragment = new AddNoteFragmentDialogFragment();
        fragment.setTargetFragment(NoteListFragment.this, ADD_NOTE_REQUEST);
        fragment.show(getFragmentManager(), "ADD_FRAGMENT");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Inserts data sent from AddNoteFragmentDialogFragment.
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            noteViewModel.insert(new Note(data.getStringExtra(AddNoteFragmentDialogFragment.EXTRA_TITLE),
                    data.getStringExtra(AddNoteFragmentDialogFragment.EXTRA_DESCRIPTION)));
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
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.note_list_menu, menu);

        this.noteViewModel.getSelectedColor().observe(this, new Observer<Color>() {
            @Override
            public void onChanged(@Nullable Color color) {
                if (color != null && menu.findItem(R.id.menu_image) != null) {
                    Drawable menuIcon = menu.findItem(R.id.menu_image).getIcon();
                    menuIcon.mutate();
                    menuIcon.setColorFilter(getResources().getColor(color.getMenuIconColor()), PorterDuff.Mode.SRC_IN);
                }
            }
        });

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
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadSettingScreen();
        // TODO: Implement a new Activity/Fragment to create a new DB table to handle app color!
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle(R.string.note_list);
    }

    private void setUpStatusBar(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(colorId);
        }
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
