package com.example.mat.roomdb_mvvm.updatenote;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.NestedScrollView;
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

import com.example.mat.roomdb_mvvm.MainActivity;
import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.color.entity.Color;
import com.example.mat.roomdb_mvvm.note.NoteViewModel;
import com.example.mat.roomdb_mvvm.note.entity.Note;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class UpdateNoteFragment extends Fragment {

    public static final String EXTRA_ID =
            "com.example.mat.roomdb_mvvm.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.example.mat.roomdb_mvvm.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.example.mat.roomdb_mvvm.EXTRA_DESCRIPTION";

    @BindView(R.id.nested_scrollview)
    NestedScrollView nestedScrollView;
    @BindView(R.id.titleET)
    EditText titleET;
    @BindView(R.id.descriptionET)
    EditText descriptionET;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private UpdateNoteViewModel updateNoteViewModel;
    private NoteViewModel noteViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_note, container, false);
        ButterKnife.bind(this, v);

        this.updateNoteViewModel = ViewModelProviders.of(this).get(UpdateNoteViewModel.class);
        this.updateNoteViewModel.getMessage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });


        this.noteViewModel = ViewModelProviders.of(this.getActivity()).get(NoteViewModel.class);
        this.noteViewModel.getSelectedColor().observe(this, new Observer<Color>() {
            @Override
            public void onChanged(@Nullable Color color) {
                titleET.setTextColor(color.getCardTitleColor());
                descriptionET.setTextColor(color.getCardDescriptionColor());
                toolbar.setBackgroundColor(color.getToolBarColor());
                toolbar.setTitleTextColor(color.getToolBarTitleColor());
                nestedScrollView.setBackgroundColor(color.getBodyBackgroundColor());
            }
        });

        setUpToolBar();
        setupNote();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.update_note_menu, menu);

        this.noteViewModel.getSelectedColor().observe(this, new Observer<Color>() {
            @Override
            public void onChanged(@Nullable Color color) {
                if (color != null && menu.findItem(R.id.update_note) != null) {
                    Drawable menuIcon = menu.findItem(R.id.update_note).getIcon();
                    menuIcon.mutate();
                    menuIcon.setColorFilter(color.getMenuIconColor(), PorterDuff.Mode.SRC_IN);
                }
            }
        });

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

        Drawable menuHomeIcon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_close, null);
        menuHomeIcon = DrawableCompat.wrap(menuHomeIcon);
        DrawableCompat.setTint(menuHomeIcon, noteViewModel.getSelectedColor().getValue().getToolBarTitleColor());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(menuHomeIcon);
        getActivity().setTitle(R.string.note_update);
    }

    private void setupNote() {
        String category = getArguments().getString("title");
        String description = getArguments().getString("description");

        titleET.setText(category);
        descriptionET.setText(description);
    }

    private void updateNote() {
        this.updateNoteViewModel.updateNote(new Note(titleET.getText().toString(),
                descriptionET.getText().toString()), this);
    }
}
