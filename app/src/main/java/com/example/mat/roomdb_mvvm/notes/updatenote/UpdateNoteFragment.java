package com.example.mat.roomdb_mvvm.notes.updatenote;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.databinding.FragmentAddUpdateNoteBinding;
import com.example.mat.roomdb_mvvm.fragmentbasecallback.BaseFragmentListener;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;
import com.example.mat.roomdb_mvvm.mainactivity.ui.MainActivity;
import com.example.mat.roomdb_mvvm.notes.note.entity.Note;

import java.util.List;

public class UpdateNoteFragment extends Fragment {

    private int noteId;
    private String noteTag;
    private String noteTitle;
    private String noteDescription;

    private Context mContext;
    private UpdateNoteFragmentListener listener;
    private UpdateNoteViewModel updateNoteViewModel;
    private FragmentAddUpdateNoteBinding viewBinding;

    public interface UpdateNoteFragmentListener extends BaseFragmentListener {
        void onNoteUpdateCompleted();
    }

    public static UpdateNoteFragment newInstance(Note note) {
        UpdateNoteFragment updateNoteFragment = new UpdateNoteFragment();

        Bundle bundle = new Bundle();
        bundle.putString("note_id", Integer.toString(note.getNoteId()));
        bundle.putString("note_tag", note.getNoteTag());
        bundle.putString("note_title", note.getNoteTitle());
        bundle.putString("note_description", note.getNoteDescription());
        updateNoteFragment.setArguments(bundle);

        return updateNoteFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            listener = (UpdateNoteFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement UpdateNoteFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String noteStringId = getArguments() != null ? getArguments().getString("note_id") : null;
        noteId = noteStringId != null ? Integer.valueOf(noteStringId) : 0;
        noteTag = getArguments() != null ? getArguments().getString("note_tag") : null;
        noteTitle = getArguments() != null ? getArguments().getString("note_title") : null;
        noteDescription = getArguments() != null ? getArguments().getString("note_description") : null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_update_note, container, false);

        setUI();

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
            case R.id.add_update_note:
                updateNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUI() {
        listener.setBackButtonVisible(true);
        listener.setToolbarTitle(getString(R.string.note_update));
        setupNote();
        setUpToolBar();
        setUpLiveData();
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
    }

    private void setupNote() {
        viewBinding.fragmentAddNoteTitleEt.setText(noteTitle);
        viewBinding.fragmentAddNoteDescriptionEt.setText(noteDescription);
    }

    private void updateNote() {
        Note oldNote = new Note(noteTag, noteTitle, noteDescription);
        oldNote.setNoteId(noteId);

        Note newNote = new Note(viewBinding.fragmentAddNoteTagS.getSelectedItem().toString(),
                viewBinding.fragmentAddNoteTitleEt.getText().toString(),
                viewBinding.fragmentAddNoteDescriptionEt.getText().toString());

        this.updateNoteViewModel.updateNote(oldNote, newNote);
    }

    private void setUpLiveData() {
        updateNoteViewModel = ViewModelProviders.of(this).get(UpdateNoteViewModel.class);
        updateNoteViewModel.getMessage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer message) {
                if (message != null) {
                    if (message == R.string.note_updated) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                    listener.onNoteUpdateCompleted();
                }
            }
        });

        updateNoteViewModel.getAllTagMenuItems().observe(this, new Observer<List<DrawerMenuItem>>() {
            @Override
            public void onChanged(List<DrawerMenuItem> drawerMenuItems) {
                if (drawerMenuItems != null && drawerMenuItems.size() != 0) {
                    ArrayAdapter<DrawerMenuItem> tagArrayAdapter = new ArrayAdapter<DrawerMenuItem>(
                            mContext, R.layout.spinner_item_text, drawerMenuItems);
                    tagArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    viewBinding.fragmentAddNoteTagS.setAdapter(tagArrayAdapter);
                    viewBinding.fragmentAddNoteTagS.setSelection(findMenuItemPosition(
                            drawerMenuItems, noteTag));
                }
            }
        });
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
