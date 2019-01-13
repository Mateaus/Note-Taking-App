package com.example.mat.roomdb_mvvm.settings;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.color.entity.Color;
import com.example.mat.roomdb_mvvm.color.ui.ColorPickerDialogFragment;
import com.example.mat.roomdb_mvvm.note.NoteViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class SettingFragment extends Fragment {

    public static final int UPDATE_STATUS_REQUEST = 3;
    public static final int UPDATE_TOOLBAR_REQUEST = 4;
    public static final int UPDATE_TOOLBAR_TITLE_REQUEST = 5;
    public static final int UPDATE_TOOLBAR_ICON_REQUEST = 6;
    public static final int UPDATE_BACKGROUND_REQUEST = 7;
    public static final int UPDATE_ADD_BACKGROUND_REQUEST = 8;
    public static final int UPDATE_ADD_ICON_REQUEST = 9;

    @BindView(R.id.statusBtn)
    Button statusBtn;
    @BindView(R.id.toolbarBtn)
    Button toolbarBtn;
    @BindView(R.id.toolbarTBtn)
    Button toolbarTBtn;
    @BindView(R.id.toolbarIBtn)
    Button toolbarIBtn;
    @BindView(R.id.backgroundBtn)
    Button backgroundBtn;
    @BindView(R.id.addBBtn)
    Button addBackgroundBtn;
    @BindView(R.id.addIBtn)
    Button addIconBtn;

    private NoteViewModel noteViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, v);

        this.noteViewModel = ViewModelProviders.of(this.getActivity()).get(NoteViewModel.class);
        this.noteViewModel.getSelectedColor().observe(this, new Observer<Color>() {
            @Override
            public void onChanged(@Nullable Color color) {
                setUpStatusBar(getResources().getColor(color.getStatusBarColor()));
                statusBtn.setBackgroundColor(getResources().getColor(color.getStatusBarColor()));
                toolbarBtn.setBackgroundColor(getResources().getColor(color.getToolBarColor()));
                toolbarTBtn.setBackgroundColor(getResources().getColor(color.getToolBarTitleColor()));
                toolbarIBtn.setBackgroundColor(getResources().getColor(color.getMenuIconColor()));
                backgroundBtn.setBackgroundColor(getResources().getColor(color.getBodyBackgroundColor()));
                addBackgroundBtn.setBackgroundColor(getResources().getColor(color.getAddButtonBackgroundColor()));
                addIconBtn.setBackgroundColor(getResources().getColor(color.getAddButtonIconColor()));
            }
        });

        return v;
    }

    @OnClick(R.id.statusBtn)
    public void handleStatusBtn() {
        ColorPickerDialogFragment fragment = new ColorPickerDialogFragment();
        fragment.setTargetFragment(SettingFragment.this, UPDATE_STATUS_REQUEST);
        fragment.show(getFragmentManager(), "statusbar");
    }

    @OnClick(R.id.toolbarBtn)
    public void handleToolBarBtn() {
        ColorPickerDialogFragment fragment = new ColorPickerDialogFragment();
        fragment.setTargetFragment(SettingFragment.this, UPDATE_TOOLBAR_REQUEST);
        fragment.show(getFragmentManager(), "toolbar");
    }

    @OnClick(R.id.toolbarTBtn)
    public void handleToolBarTitleBtn() {
        ColorPickerDialogFragment fragment = new ColorPickerDialogFragment();
        fragment.setTargetFragment(SettingFragment.this, UPDATE_TOOLBAR_TITLE_REQUEST);
        fragment.show(getFragmentManager(), "toolbar_title");
    }

    @OnClick(R.id.toolbarIBtn)
    public void handleToolBarIconBtn() {
        ColorPickerDialogFragment fragment = new ColorPickerDialogFragment();
        fragment.setTargetFragment(SettingFragment.this, UPDATE_TOOLBAR_ICON_REQUEST);
        fragment.show(getFragmentManager(), "toolbar_icon");
    }

    @OnClick(R.id.backgroundBtn)
    public void handleBackgroundBtn() {
        ColorPickerDialogFragment fragment = new ColorPickerDialogFragment();
        fragment.setTargetFragment(SettingFragment.this, UPDATE_BACKGROUND_REQUEST);
        fragment.show(getFragmentManager(), "background");
    }

    @OnClick(R.id.addBBtn)
    public void handleAddBackgroundButton() {
        ColorPickerDialogFragment fragment = new ColorPickerDialogFragment();
        fragment.setTargetFragment(SettingFragment.this, UPDATE_ADD_BACKGROUND_REQUEST);
        fragment.show(getFragmentManager(), "add_background");
    }

    @OnClick(R.id.addIBtn)
    public void handleAddIconButton() {
        ColorPickerDialogFragment fragment = new ColorPickerDialogFragment();
        fragment.setTargetFragment(SettingFragment.this, UPDATE_ADD_ICON_REQUEST);
        fragment.show(getFragmentManager(), "add_icon");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Color color = this.noteViewModel.getSelectedColor().getValue();

        if (requestCode == UPDATE_STATUS_REQUEST && resultCode == RESULT_OK) {
            int status = Integer.valueOf(data.getStringExtra(ColorPickerDialogFragment.EXTRA_COLOR));
            color.setStatusBarColor(status);
            noteViewModel.updateColor(color);
        }

        if (requestCode == UPDATE_TOOLBAR_REQUEST && resultCode == RESULT_OK) {
            int toolbar = Integer.valueOf(data.getStringExtra(ColorPickerDialogFragment.EXTRA_COLOR));
            color.setToolBarColor(toolbar);
            noteViewModel.updateColor(color);
        }

        if (requestCode == UPDATE_TOOLBAR_TITLE_REQUEST && resultCode == RESULT_OK) {
            int toolbarTitle = Integer.valueOf(data.getStringExtra(ColorPickerDialogFragment.EXTRA_COLOR));
            color.setToolBarTitleColor(toolbarTitle);
            noteViewModel.updateColor(color);
        }

        if (requestCode == UPDATE_TOOLBAR_ICON_REQUEST && resultCode == RESULT_OK) {
            int toolbarIcon = Integer.valueOf(data.getStringExtra(ColorPickerDialogFragment.EXTRA_COLOR));
            color.setMenuIconColor(toolbarIcon);
            noteViewModel.updateColor(color);
        }

        if (requestCode == UPDATE_BACKGROUND_REQUEST && resultCode == RESULT_OK) {
            int background = Integer.valueOf(data.getStringExtra(ColorPickerDialogFragment.EXTRA_COLOR));
            color.setBodyBackgroundColor(background);
            noteViewModel.updateColor(color);
        }

        if (requestCode == UPDATE_ADD_BACKGROUND_REQUEST && resultCode == RESULT_OK) {
            int addBackground = Integer.valueOf(data.getStringExtra(ColorPickerDialogFragment.EXTRA_COLOR));
            color.setAddButtonBackgroundColor(addBackground);
            noteViewModel.updateColor(color);
        }

        if (requestCode == UPDATE_ADD_ICON_REQUEST && resultCode == RESULT_OK) {
            int addIcon = Integer.valueOf(data.getStringExtra(ColorPickerDialogFragment.EXTRA_COLOR));
            color.setAddButtonIconColor(addIcon);
            noteViewModel.updateColor(color);
        }
    }

    private void setUpStatusBar(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(colorId);
        } else {
            statusBtn.setEnabled(false);
        }
    }
}
