package com.example.mat.note_keeper.notes.addnote;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mat.note_keeper.mainactivity.entity.Tag;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;
import com.example.mat.note_keeper.mainactivity.model.DrawerLayoutMenuItem;
import com.example.mat.note_keeper.mainactivity.ui.MainActivity;
import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.notes.note.entity.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNoteFragment extends Fragment {

    public static final String EXTRA_TAG =
            "com.example.mat.roomdb_mvvm.EXTRA_TAG";

    public static final String EXTRA_TITLE =
            "com.example.mat.roomdb_mvvm.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.example.mat.roomdb_mvvm.EXTRA_DESCRIPTION";

    @BindView(R.id.titleET)
    EditText titleET;
    @BindView(R.id.descriptionET)
    EditText descriptionET;
    @BindView(R.id.tagS)
    Spinner tagS;

    private AddNoteViewModel addNoteViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_note, container, false);
        ButterKnife.bind(this, v);
        showBackButton(true);

        this.addNoteViewModel = ViewModelProviders.of(this).get(AddNoteViewModel.class);
        this.addNoteViewModel.getMessage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer message) {
                if (message != null) {
                    if (message == R.string.note_add) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                    getFragmentManager().popBackStack();
                }
            }
        });

        this.addNoteViewModel.getAllTagMenuItems().observe(this, new Observer<List<DrawerLayoutMenuItem>>() {
            @Override
            public void onChanged(List<DrawerLayoutMenuItem> drawerLayoutMenuItems) {
                if (drawerLayoutMenuItems != null && drawerLayoutMenuItems.size() != 0) {
                    Collections.reverse(drawerLayoutMenuItems);
                    ArrayAdapter<DrawerLayoutMenuItem> tagArrayAdapter = new ArrayAdapter<DrawerLayoutMenuItem>(getContext(), R.layout.spinner_item_text, drawerLayoutMenuItems);
                    tagArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tagS.setAdapter(tagArrayAdapter);
                }
            }
        });

        setUpToolBar();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.update_note_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_note:
                int menuId = Integer.valueOf(getArguments().getString("menu_id"));
                String menuName = getArguments().getString("menu_name");
                int menuSize = Integer.valueOf(getArguments().getString("menu_size"));
                String menuIcon = getArguments().getString("menu_icon");

                addNote(new DrawerLayoutMenuItem(menuId, menuName, menuSize, menuIcon));
                return true;
            default:
                getFragmentManager().popBackStack();
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.note_add);
    }

    private void addNote(DrawerLayoutMenuItem drawerLayoutMenuItem) {
        this.addNoteViewModel.addNote(new Note(tagS.getSelectedItem().toString(),
                titleET.getText().toString(), descriptionET.getText().toString()), drawerLayoutMenuItem);
    }

    private void showBackButton(Boolean enable) {
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.showBackButton(enable);
    }
}
