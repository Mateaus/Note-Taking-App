package com.example.mat.note_keeper.category_note_app.note_section.note.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.mat.note_keeper.MainActivity;
import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.category_note_app.note_section.addnote.AddNoteFragment;
import com.example.mat.note_keeper.category_note_app.note_section.note.NoteViewModel;
import com.example.mat.note_keeper.category_note_app.note_section.note.NoteViewModelFactory;
import com.example.mat.note_keeper.category_note_app.note_section.note.adapters.NoteAdapter;
import com.example.mat.note_keeper.category_note_app.note_section.note.entity.Note;
import com.example.mat.note_keeper.category_note_app.note_section.updatenote.UpdateNoteFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class NoteListFragment extends Fragment implements OnItemClickListener {

    public static final int ADD_NOTE_REQUEST = 3;
    public static final int UPDATE_NOTE_REQUEST = 4;

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

        int catalogueId = Integer.valueOf(getArguments().getString("id"));
        this.noteViewModel = ViewModelProviders.of(this,
                new NoteViewModelFactory(this.getActivity().getApplication(), catalogueId)).get(NoteViewModel.class);
        this.noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                for (Note i : notes) {
                    System.out.println("Category id:" + i.getC_id());
                    System.out.println("Note id:" + i.getN_id());
                    System.out.println("Note title:" + i.getNtitle());
                    System.out.println("Note description:" + i.getNdescription());
                    System.out.println("Note date:" + i.getNdate());

                }
                noteAdapter.submitList(notes);
            }
        });

/*
        this.noteViewModel.getSelectedColor().observe(this, new Observer<Color>() {
            @Override
            public void onChanged(@Nullable Color color) {
                if (color != null) {
                    setUpStatusBar(color.getStatusBarColor());
                    addBtn.setBackgroundTintList(ColorStateList.valueOf(color.getAddButtonBackgroundColor()));
                    addBtn.setColorFilter(color.getAddButtonIconColor());
                    toolbar.setBackgroundColor(color.getToolBarColor());
                    toolbar.setTitleTextColor(color.getToolBarTitleColor());
                    recyclerView.setBackgroundColor(color.getBodyBackgroundColor());
                }
            }
        });*/

        setUpItemTouchHelper();

        return v;
    }

    @OnClick(R.id.add_note_btn)
    public void addButtonHandler() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadAddNoteScreen(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Inserts data sent from AddNoteDialogFragment.
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            int catalogueId = Integer.valueOf(getArguments().getString("id"));

            Note addNote = new Note(data.getStringExtra(AddNoteFragment.EXTRA_TITLE),
                    data.getStringExtra(AddNoteFragment.EXTRA_DESCRIPTION));
            addNote.setC_id(catalogueId);

            this.noteViewModel.insert(addNote);
        }
        // Updates data sent from UpdateNoteFragment.
        if (requestCode == UPDATE_NOTE_REQUEST && resultCode == RESULT_OK) {
            int cid = Integer.valueOf(data.getStringExtra(UpdateNoteFragment.EXTRA_CID));
            int nid = Integer.valueOf(data.getStringExtra(UpdateNoteFragment.EXTRA_NID));

            Note updateNote = new Note(data.getStringExtra(UpdateNoteFragment.EXTRA_TITLE),
                    data.getStringExtra(UpdateNoteFragment.EXTRA_DESCRIPTION));
            updateNote.setC_id(cid);
            updateNote.setN_id(nid);
            this.noteViewModel.update(updateNote);
        }
    }

    @Override
    public void onItemClick(Note note) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadUpdateNoteScreen(note, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                getFragmentManager().popBackStack();
                return super.onOptionsItemSelected(item);
        }
    }
/*
    @Override
    public int changeCardViewColor() {
        return this.noteViewModel.getSelectedColor().getValue().getCardColor();
    }

    @Override
    public int changeTitleColor() {
        return this.noteViewModel.getSelectedColor().getValue().getCardTitleColor();
    }

    @Override
    public int changeDescriptionColor() {
        return this.noteViewModel.getSelectedColor().getValue().getCardDescriptionColor();
    }

    @Override
    public int changeDateColor() {
        return this.noteViewModel.getSelectedColor().getValue().getCardDateColor();
    }*/

    private void setUpToolBar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable menuHomeIcon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_close, null);
        menuHomeIcon = DrawableCompat.wrap(menuHomeIcon);
        //DrawableCompat.setTint(menuHomeIcon, noteViewModel.getSelectedColor().getValue().getToolBarTitleColor());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(menuHomeIcon);
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
