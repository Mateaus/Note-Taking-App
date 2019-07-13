package com.example.mat.roomdb_mvvm.mainactivity.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.color.entity.Theme;
import com.example.mat.roomdb_mvvm.color.ui.ColorFragment;
import com.example.mat.roomdb_mvvm.databinding.ActivityMainBinding;
import com.example.mat.roomdb_mvvm.expandablerecyclerview.models.ExpandableGroup;
import com.example.mat.roomdb_mvvm.mainactivity.MainViewModel;
import com.example.mat.roomdb_mvvm.mainactivity.adapter.CategoryAdapter;
import com.example.mat.roomdb_mvvm.mainactivity.adapter.CategoryEditAdapter;
import com.example.mat.roomdb_mvvm.mainactivity.adapter.ItemAdapter;
import com.example.mat.roomdb_mvvm.mainactivity.entity.Tag;
import com.example.mat.roomdb_mvvm.mainactivity.entity.TagCategory;
import com.example.mat.roomdb_mvvm.mainactivity.entity.TagEditCategory;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnMenuItemClickListener;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnNewTagClickListener;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnTagCategoryEditClickListener;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnTagClickListener;
import com.example.mat.roomdb_mvvm.mainactivity.listener.StatusBarListener;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;
import com.example.mat.roomdb_mvvm.mainactivity.model.MergedMenu;
import com.example.mat.roomdb_mvvm.notes.addnote.AddNoteFragment;
import com.example.mat.roomdb_mvvm.notes.note.entity.Note;
import com.example.mat.roomdb_mvvm.notes.note.ui.NoteListFragment;
import com.example.mat.roomdb_mvvm.notes.updatenote.UpdateNoteFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StatusBarListener, OnTagClickListener,
        OnMenuItemClickListener, OnNewTagClickListener, OnTagCategoryEditClickListener {

    public static String CURR_TAG = "All Notes";
    public final static String ADD_TAG = "ADD_TAG";
    public final static String EDIT_TAG = "EDIT_TAG";
    public final static String DELETE_TAG = "DELETE_TAG";

    private FragmentManager fragmentManager;
    private MainViewModel mainViewModel;
    private ActionBarDrawerToggle drawerToggle;
    private boolean toolBarNavigationListenerIsRegistered = false;

    private ItemAdapter itemAdapter;
    private CategoryAdapter expandableAdapter;
    private CategoryEditAdapter expandableEditAdapter;

    private ActivityMainBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initUI();

        /*
         * Update the activity's Theme and StatusBar to affect all the fragments in this activity.
         * This also ensures all fragments will remain updated with the selected color theme
         * from the database when we decide to change screen orientation while being inside
         * the fragments.
         */
        this.mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        this.mainViewModel.getMergedMenuLiveData().observe(this, new Observer<MergedMenu>() {
            @Override
            public void onChanged(MergedMenu mergedMenu) {
                if (mergedMenu == null && !mergedMenu.isComplete()) {
                    return;
                }

                if (mergedMenu.isComplete()) {
                    itemAdapter.submitList(mergedMenu.getMenuList());
                }
            }
        });

        this.mainViewModel.getAllTagMenuItems().observe(this, new Observer<List<DrawerMenuItem>>() {
            @Override
            public void onChanged(List<DrawerMenuItem> drawerMenuItems) {
                if (drawerMenuItems != null && drawerMenuItems.size() != 0) {
                    expandableAdapter.setTagList(drawerMenuItems);
                    expandableEditAdapter.setTagList(drawerMenuItems);

                    // Begins expanded keeps it expanded when changes happen
                    if (!expandableAdapter.isGroupExpanded(0)) {
                        expandableAdapter.toggleGroup(0);
                    }

                    if (!expandableEditAdapter.isGroupExpanded(0)) {
                        expandableEditAdapter.toggleGroup(0);
                    }
                }
            }
        });

        this.mainViewModel.getTheme().observe(this, new Observer<Theme>() {
            @Override
            public void onChanged(Theme theme) {
                if (theme != null) {
                    setUpStatusBar(getResources().getColor(theme.getPrimaryDarkColor()));
                    setTheme(theme.getThemeStyle());
                    viewBinding.toolbar.setBackgroundColor(getResources().getColor(theme.getPrimaryColor()));
                    viewBinding.activityMainNh.setBackgroundColor(getResources().getColor(theme.getPrimaryDarkColor()));
                    viewBinding.activityMainNv.setBackgroundColor(getResources().getColor(theme.getPrimaryColor()));
                    expandableAdapter.updateFooterButtonColor(theme.getPrimaryDarkColor());
                }
            }
        });

        fragmentManager = getSupportFragmentManager();
        Fragment mainFragment = (Fragment) fragmentManager.findFragmentById(R.id.activity_main_fl);

        if (mainFragment == null) {
            NoteListFragment noteListFragment = new NoteListFragment();

            Bundle bundle = new Bundle();
            bundle.putString("menu_id", "1");
            bundle.putString("menu_name", "All Notes");
            bundle.putString("menu_icon", "note_icon");
            noteListFragment.setArguments(bundle);

            fragmentManager.beginTransaction().add(R.id.activity_main_fl, noteListFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        expandableAdapter.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        expandableAdapter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onTagClick(DrawerMenuItem tag) {
        loadNoteScreen(tag);
        viewBinding.activityMainActivityDl.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onMenuItemClick(DrawerMenuItem drawerMenuItem) {
        loadNoteScreen(drawerMenuItem);
        viewBinding.activityMainActivityDl.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onNewTagClick(TagCategory tagCategory) {
        TagAddUpdateDialogFragment tagAddUpdateDialogFragment = new TagAddUpdateDialogFragment();
        tagAddUpdateDialogFragment.show(getSupportFragmentManager(), ADD_TAG);
    }

    @Override
    public void onMenuUpdateItemClick(DrawerMenuItem drawerMenuItem, int position) {
        TagAddUpdateDialogFragment tagAddUpdateDialogFragment = new TagAddUpdateDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("menu_tag_image", drawerMenuItem.getMenuItemImage());
        bundle.putString("menu_tag_name", drawerMenuItem.getMenuItemName());
        bundle.putString("menu_tag_id", String.valueOf(drawerMenuItem.getMenuItemId()));
        bundle.putString("menu_tag_size", String.valueOf(drawerMenuItem.getMenuItemSize()));
        tagAddUpdateDialogFragment.setArguments(bundle);

        tagAddUpdateDialogFragment.show(getSupportFragmentManager(), EDIT_TAG);
    }

    @Override
    public void onMenuDeleteClick(DrawerMenuItem drawerMenuItem, int position) {
        TagDeleteDialogFragment tagEditDialogFragment = new TagDeleteDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("menu_tag_image", drawerMenuItem.getMenuItemImage());
        bundle.putString("menu_tag_name", drawerMenuItem.getMenuItemName());
        bundle.putString("menu_tag_id", String.valueOf(drawerMenuItem.getMenuItemId()));
        bundle.putString("menu_tag_size", String.valueOf(drawerMenuItem.getMenuItemSize()));
        tagEditDialogFragment.setArguments(bundle);

        tagEditDialogFragment.show(getSupportFragmentManager(), DELETE_TAG);
    }

    @Override
    public void onTagEditClickListener(ExpandableGroup group) {
        if (group.getOption().equals("Edit")) {
            viewBinding.activityMainExpandableRv.setAdapter(expandableEditAdapter);
        } else {
            viewBinding.activityMainExpandableRv.setAdapter(expandableAdapter);
        }
    }

    /**
     * setUpStatusBar will change the status bar color and the Task Description's
     * icon and bar color.
     * To ensure this feature works, it checks if the api version is 21(LOLLIPOP) or higher.
     *
     * @param colorId - color id coming from getResource().getColor(theme.getPrimaryDarkColor()).
     *                where theme.getPrimaryDarkColor() returns a color from R.color.colorName.
     */

    public void setUpStatusBar(int colorId) {
        // Checks if api level is higher than LOLLIPOP(api 21)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Change the status bar color by the colorId passed.
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(colorId);

            /*
             * Changes the task description's icon to the transparent icon
             * and the color based on colorId.
             */
            Bitmap bitmapIcon = BitmapFactory.decodeResource(getResources(), R.drawable.novus);
            setTaskDescription(
                    new ActivityManager.TaskDescription(null, bitmapIcon, colorId)
            );
        }
    }

    public void loadNoteScreen(DrawerMenuItem drawerMenuItem) {
        if (!CURR_TAG.equals(drawerMenuItem.getMenuItemName())) {
            NoteListFragment noteListFragment = new NoteListFragment();

            Bundle bundle = new Bundle();
            bundle.putString("menu_id", String.valueOf(drawerMenuItem.getMenuItemId()));
            bundle.putString("menu_name", drawerMenuItem.getMenuItemName());
            bundle.putString("menu_size", String.valueOf(drawerMenuItem.getMenuItemSize()));
            bundle.putString("menu_icon", drawerMenuItem.getMenuItemImage());
            noteListFragment.setArguments(bundle);


            fragmentManager.beginTransaction()
                    .replace(R.id.activity_main_fl, noteListFragment).commit();
            CURR_TAG = drawerMenuItem.getMenuItemName();
        }
    }

    public void loadAddNoteScreen(DrawerMenuItem drawerMenuItem) {
        AddNoteFragment addNoteFragment = new AddNoteFragment();

        Bundle bundle = new Bundle();
        bundle.putString("menu_id", String.valueOf(drawerMenuItem.getMenuItemId()));
        bundle.putString("menu_name", drawerMenuItem.getMenuItemName());
        bundle.putString("menu_size", String.valueOf(drawerMenuItem.getMenuItemSize()));
        bundle.putString("menu_icon", drawerMenuItem.getMenuItemImage());
        addNoteFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fl, addNoteFragment).addToBackStack(null).commit();
    }

    public void loadUpdateNoteScreen(Note note) {
        UpdateNoteFragment updateNoteFragment = new UpdateNoteFragment();

        Bundle bundle = new Bundle();
        bundle.putString("note_id", Integer.toString(note.getNoteId()));
        bundle.putString("note_tag", note.getNoteTag());
        bundle.putString("note_title", note.getNoteTitle());
        bundle.putString("note_description", note.getNoteDescription());
        updateNoteFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fl, updateNoteFragment).addToBackStack(null).commit();
    }

    public void loadColorScreen() {
        ColorFragment colorFragment = new ColorFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fl, colorFragment)
                .addToBackStack(null)
                .commit();
    }

    public void showBackButton(Boolean enable) {
        if (enable) {
            viewBinding.activityMainActivityDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            drawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (!toolBarNavigationListenerIsRegistered) {
                drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }

            toolBarNavigationListenerIsRegistered = true;
        } else {
            // TODO : Need to maybe modify this later
            hideSoftKeyboard(getWindow().getDecorView().getRootView());
            viewBinding.activityMainActivityDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.setToolbarNavigationClickListener(null);
            toolBarNavigationListenerIsRegistered = false;
            fragmentManager.popBackStack();
        }
    }

    private void initUI() {
        setSupportActionBar(viewBinding.toolbar);
        setUpNavigationView();
        setBurgerToggle();
        setAdapter();
        setRecyclerView();
        viewBinding.activityMainActivityDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
    }

    private void setUpNavigationView() {
        viewBinding.activityMainNv.setItemIconTintList(null);
    }

    private void setBurgerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, viewBinding.activityMainActivityDl, viewBinding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        viewBinding.activityMainActivityDl.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void setAdapter() {
        itemAdapter = new ItemAdapter(this);
        expandableAdapter = new CategoryAdapter(
                new ArrayList<TagCategory>(Arrays.asList(
                        new TagCategory("Tags", "Edit", new ArrayList<Tag>()))
                ), this, this, this);
        expandableEditAdapter = new CategoryEditAdapter(
                new ArrayList<TagEditCategory>(Arrays.asList(
                        new TagEditCategory("Tags", "Return", new ArrayList<Tag>()))
                ), this, this);
    }

    private void setRecyclerView() {
        viewBinding.activityMainSingleRv.setLayoutManager(new LinearLayoutManager(this));
        viewBinding.activityMainSingleRv.setAdapter(itemAdapter);
        viewBinding.activityMainExpandableRv.setLayoutManager(new LinearLayoutManager(this));
        viewBinding.activityMainExpandableRv.setAdapter(expandableAdapter);
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
