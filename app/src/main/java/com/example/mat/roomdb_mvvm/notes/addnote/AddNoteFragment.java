package com.example.mat.roomdb_mvvm.notes.addnote;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.databinding.FragmentAddUpdateNoteBinding;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;
import com.example.mat.roomdb_mvvm.mainactivity.ui.MainActivity;
import com.example.mat.roomdb_mvvm.notes.note.entity.Note;

import java.util.List;

public class AddNoteFragment extends Fragment {

    private AddNoteViewModel addNoteViewModel;
    private FragmentAddUpdateNoteBinding viewBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_update_note, container, false);
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

        this.addNoteViewModel.getAllTagMenuItems().observe(this, new Observer<List<DrawerMenuItem>>() {
            @Override
            public void onChanged(List<DrawerMenuItem> drawerMenuItems) {
                if (drawerMenuItems != null && drawerMenuItems.size() != 0) {
                    ArrayAdapter<DrawerMenuItem> tagArrayAdapter = new ArrayAdapter<DrawerMenuItem>(getContext(), R.layout.spinner_item_text, drawerMenuItems);
                    tagArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    viewBinding.fragmentAddNoteTagS.setAdapter(tagArrayAdapter);
                    viewBinding.fragmentAddNoteTagS.setSelection(findMenuItemPosition(drawerMenuItems, getArguments().getString("menu_name")));
                }
            }
        });

        setUpToolBar();

        return viewBinding.getRoot();
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

                addNote(new DrawerMenuItem(menuId, menuName, menuSize, menuIcon));
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

    private void addNote(DrawerMenuItem drawerMenuItem) {
        this.addNoteViewModel.addNote(new Note(viewBinding.fragmentAddNoteTagS.getSelectedItem().toString(),
                viewBinding.fragmentAddNoteTitleEt.getText().toString(),
                viewBinding.fragmentAddNoteDescriptionEt.getText().toString()), drawerMenuItem);
    }

    private void showBackButton(Boolean enable) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.showBackButton(enable);
    }

    private int findMenuItemPosition(List<DrawerMenuItem> drawerMenuItems, String tagName) {
        for (int i = 0; i < drawerMenuItems.size(); i++) {
            if (drawerMenuItems.get(i).getMenuItemName().equals(tagName)) {
                return i;
            }
        }
        return drawerMenuItems.size() - 1;
    }
}
