package com.example.mat.roomdb_mvvm.notes.addnote;


import android.content.Context;
import android.graphics.Color;
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
import com.example.mat.roomdb_mvvm.color.entity.Theme;
import com.example.mat.roomdb_mvvm.databinding.FragmentAddUpdateNoteBinding;
import com.example.mat.roomdb_mvvm.databinding.SpinnerItemTextBinding;
import com.example.mat.roomdb_mvvm.fragmentbasecallback.BaseFragmentListener;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;
import com.example.mat.roomdb_mvvm.mainactivity.model.MenuItemThemeWrapper;
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

        this.addNoteViewModel.getAllThemeAndTagMenuItems().observe(this, new Observer<MenuItemThemeWrapper>() {
            @Override
            public void onChanged(MenuItemThemeWrapper menuItemThemeWrapper) {
                if (menuItemThemeWrapper != null && menuItemThemeWrapper.getDrawerMenuItem() != null
                        && menuItemThemeWrapper.getTheme() != null) {

                    final Theme theme = menuItemThemeWrapper.getTheme();
                    ArrayAdapter<DrawerMenuItem> tagArrayAdapter = new ArrayAdapter<DrawerMenuItem>(
                            mContext, R.layout.spinner_item_text, R.id.spinner_tv, menuItemThemeWrapper.getDrawerMenuItem()) {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            SpinnerItemViewHolder spinnerIViewHolder =
                                    new SpinnerItemViewHolder(super.getView(position, convertView, parent));
                            if (theme.isDarkTheme()) {
                                spinnerIViewHolder.viewBinding.spinnerTv.setTextColor(Color.WHITE);
                                spinnerIViewHolder.viewBinding.spinnerIv.setImageDrawable(getResources().getDrawable(
                                        R.drawable.drop_down_arrow_light));
                            } else {
                                spinnerIViewHolder.viewBinding.spinnerTv.setTextColor(
                                        getResources().getColor(android.R.color.tab_indicator_text));
                                spinnerIViewHolder.viewBinding.spinnerIv.setImageDrawable(getResources().getDrawable(
                                        R.drawable.drop_down_arrow_dark));
                            }

                            return spinnerIViewHolder.viewBinding.getRoot();
                        }
                    };
                    tagArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                    viewBinding.fragmentAddNoteTagS.setAdapter(tagArrayAdapter);
                    viewBinding.fragmentAddNoteTagS.setSelection(findMenuItemPosition(
                            menuItemThemeWrapper.getDrawerMenuItem(), menuName));

                    if (theme.isDarkTheme()) {
                        viewBinding.fragmentAddNoteTitleEt.setTextColor(Color.WHITE);
                        viewBinding.fragmentAddNoteTitleEt.setHintTextColor(Color.WHITE);
                        viewBinding.fragmentAddNoteDescriptionEt.setTextColor(Color.WHITE);
                        viewBinding.fragmentAddNoteDescriptionEt.setHintTextColor(Color.WHITE);
                    } else {
                        viewBinding.fragmentAddNoteTitleEt.setTextColor(
                                getResources().getColor(android.R.color.tab_indicator_text));
                        viewBinding.fragmentAddNoteDescriptionEt.setTextColor(
                                getResources().getColor(android.R.color.tab_indicator_text));
                    }

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

    public static class SpinnerItemViewHolder {

        private SpinnerItemTextBinding viewBinding;

        private SpinnerItemViewHolder(View view) {
            viewBinding = DataBindingUtil.bind(view);
        }
    }
}
