package com.example.mat.roomdb_mvvm.notes.addnote;


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

public class AddNoteFragment extends Fragment {

    private int menuId;
    private String menuName;
    private int menuSize;
    private String menuIcon;

    private Context mContext;
    private AddNoteFragmentListener listener;
    private AddNoteViewModel addNoteViewModel;
    private FragmentAddUpdateNoteBinding viewBinding;

    public interface AddNoteFragmentListener extends BaseFragmentListener {
        void onNoteCreationCompleted();
    }

    public static AddNoteFragment newInstance(DrawerMenuItem drawerMenuItem) {
        AddNoteFragment addNoteFragment = new AddNoteFragment();

        Bundle bundle = new Bundle();
        bundle.putString("menu_id", String.valueOf(drawerMenuItem.getMenuItemId()));
        bundle.putString("menu_name", drawerMenuItem.getMenuItemName());
        bundle.putString("menu_size", String.valueOf(drawerMenuItem.getMenuItemSize()));
        bundle.putString("menu_icon", drawerMenuItem.getMenuItemImage());
        addNoteFragment.setArguments(bundle);

        return addNoteFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            listener = (AddNoteFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement AddNoteFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String menuStringId = getArguments() != null ? getArguments().getString("menu_id") : null;
        menuId = menuStringId != null ? Integer.valueOf(menuStringId) : 0;
        menuName = getArguments() != null ? getArguments().getString("menu_name") : "";
        String menuStringSize = getArguments() != null ? getArguments().getString("menu_size") : null;
        menuSize = menuStringSize != null ? Integer.valueOf(menuStringSize) : 0;
        menuIcon = getArguments() != null ? getArguments().getString("menu_icon") : "";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
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
                addNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUI() {
        listener.setBackButtonVisible(true);
        listener.setToolbarTitle(getString(R.string.note_add));

        setUpToolBar();

        this.addNoteViewModel = ViewModelProviders.of(this).get(AddNoteViewModel.class);
        this.addNoteViewModel.getMessage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer message) {
                if (message != null) {
                    if (message == R.string.note_created) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                    listener.onNoteCreationCompleted();
                }
            }
        });

        this.addNoteViewModel.getAllTagMenuItems().observe(this, new Observer<List<DrawerMenuItem>>() {
            @Override
            public void onChanged(List<DrawerMenuItem> drawerMenuItems) {
                if (drawerMenuItems != null && drawerMenuItems.size() != 0) {
                    ArrayAdapter<DrawerMenuItem> tagArrayAdapter = new ArrayAdapter<DrawerMenuItem>(
                            mContext, R.layout.spinner_item_text, drawerMenuItems);
                    tagArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    viewBinding.fragmentAddNoteTagS.setAdapter(tagArrayAdapter);
                    viewBinding.fragmentAddNoteTagS.setSelection(findMenuItemPosition(
                            drawerMenuItems, menuName));
                }
            }
        });
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
    }

    private void addNote() {
        this.addNoteViewModel.addNote(new Note(viewBinding.fragmentAddNoteTagS.getSelectedItem().toString(),
                viewBinding.fragmentAddNoteTitleEt.getText().toString(),
                viewBinding.fragmentAddNoteDescriptionEt.getText().toString()));
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
