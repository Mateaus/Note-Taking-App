package com.example.mat.roomdb_mvvm.notes.updatenote;

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

public class UpdateNoteFragment extends Fragment {

    private UpdateNoteViewModel updateNoteViewModel;
    private FragmentAddUpdateNoteBinding viewBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_update_note, container, false);
        showBackButton(true);

        setUpToolBar();

        this.updateNoteViewModel = ViewModelProviders.of(this).get(UpdateNoteViewModel.class);
        this.updateNoteViewModel.getMessage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer message) {
                if (message != null) {
                    if (message == R.string.note_updated) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                    getFragmentManager().popBackStack();
                }
            }
        });

        this.updateNoteViewModel.getAllTagMenuItems().observe(this, new Observer<List<DrawerMenuItem>>() {
            @Override
            public void onChanged(List<DrawerMenuItem> drawerMenuItems) {
                if (drawerMenuItems != null && drawerMenuItems.size() != 0) {
                    ArrayAdapter<DrawerMenuItem> tagArrayAdapter = new ArrayAdapter<DrawerMenuItem>(getContext(), R.layout.spinner_item_text, drawerMenuItems);
                    tagArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    viewBinding.fragmentAddNoteTagS.setAdapter(tagArrayAdapter);
                    viewBinding.fragmentAddNoteTagS.setSelection(findMenuItemPosition(drawerMenuItems, getArguments().getString("note_tag")));
                }
            }
        });

        setupNote();

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
                updateNote();
                return true;
            default:
                getFragmentManager().popBackStack();
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.note_update);
    }

    private void setupNote() {
        String noteTag = getArguments().getString("note_tag");
        String noteTitle = getArguments().getString("note_title");
        String noteDescription = getArguments().getString("note_description");

        viewBinding.fragmentAddNoteTitleEt.setText(noteTitle);
        viewBinding.fragmentAddNoteDescriptionEt.setText(noteDescription);
    }

    private void updateNote() {
        String noteId = getArguments().getString("note_id");
        String noteTag = getArguments().getString("note_tag");
        String noteTitle = getArguments().getString("note_title");
        String noteDescription = getArguments().getString("note_description");

        Note oldNote = new Note(noteTag, noteTitle, noteDescription);
        oldNote.setNoteId(Integer.valueOf(noteId));

        Note newNote = new Note(viewBinding.fragmentAddNoteTagS.getSelectedItem().toString(),
                viewBinding.fragmentAddNoteTitleEt.getText().toString(),
                viewBinding.fragmentAddNoteDescriptionEt.getText().toString());

        this.updateNoteViewModel.updateNote(oldNote, newNote);
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
        return 0;
    }
}
