package com.example.mat.note_keeper.mainactivity.ui;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.color.entity.Theme;
import com.example.mat.note_keeper.color.ui.ColorFragment;
import com.example.mat.note_keeper.mainactivity.MainViewModel;
import com.example.mat.note_keeper.mainactivity.adapter.CategoryAdapter;
import com.example.mat.note_keeper.mainactivity.adapter.ItemAdapter;
import com.example.mat.note_keeper.mainactivity.entity.Tag;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;
import com.example.mat.note_keeper.mainactivity.listener.OnMenuItemClickListener;
import com.example.mat.note_keeper.mainactivity.listener.OnTagClickListener;
import com.example.mat.note_keeper.mainactivity.listener.StatusBarListener;
import com.example.mat.note_keeper.mainactivity.model.MenuItem;
import com.example.mat.note_keeper.notes.addnote.AddNoteFragment;
import com.example.mat.note_keeper.notes.note.entity.Note;
import com.example.mat.note_keeper.notes.note.ui.NoteListFragment;
import com.example.mat.note_keeper.notes.updatenote.UpdateNoteFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements StatusBarListener, OnTagClickListener, OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    @BindView(R.id.single_rv)
    RecyclerView singleRv;

    @BindView(R.id.expandable_rv)
    RecyclerView expandableRv;

    private FragmentManager fragmentManager;
    private MainViewModel mainViewModel;
    private ActionBarDrawerToggle drawerToggle;
    private boolean toolBarNavigationListenerIsRegistered = false;

    private ItemAdapter itemAdapter;
    private CategoryAdapter expandableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setUpNavigationView();
        setBurgerToggle();
        setAdapter();
        setRecyclerView();

        /*
         * Update the activity's Theme and StatusBar to affect all the fragments in this activity.
         * This also ensures all fragments will remain updated with the selected color theme
         * from the database when we decide to change screen orientation while being inside
         * the fragments.
         */
        this.mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        this.mainViewModel.getAllMenuItems().observe(this, new Observer<List<MenuItem>>() {
            @Override
            public void onChanged(List<MenuItem> menuItems) {
                if (menuItems != null) {
                    itemAdapter.submitList(menuItems);
                }
            }
        });

        this.mainViewModel.getTheme().observe(this, new Observer<Theme>() {
            @Override
            public void onChanged(Theme theme) {
                if (theme != null) {
                    setUpStatusBar(getResources().getColor(theme.getPrimaryDarkColor()));
                    setTheme(theme.getThemeStyle());
                    toolbar.setBackgroundColor(getResources().getColor(theme.getPrimaryColor()));
                    linearLayout.setBackgroundColor(getResources().getColor(theme.getPrimaryDarkColor()));
                    navigationView.setBackgroundColor(getResources().getColor(theme.getPrimaryColor()));
                }
            }
        });

        this.mainViewModel.getAllTagCategories().observe(this, new Observer<List<TagCategory>>() {
            @Override
            public void onChanged(List<TagCategory> tagCategories) {
                if (tagCategories != null) {
                    expandableAdapter.setTagCategories(tagCategories);
                }
            }
        });

        // TODO: Increment the counter of the nav drawer here in the activity

        fragmentManager = getSupportFragmentManager();
        Fragment mainFragment = (Fragment) fragmentManager.findFragmentById(R.id.note_container);

        if (mainFragment == null) {
            NoteListFragment noteListFragment = new NoteListFragment();

            Bundle bundle = new Bundle();
            bundle.putString("menu_id", "1");
            bundle.putString("menu_name", "All Notes");
            noteListFragment.setArguments(bundle);

            fragmentManager.beginTransaction().add(R.id.note_container, noteListFragment).commit();
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

    public void loadNoteScreen(MenuItem menuItem) {
        NoteListFragment noteListFragment = new NoteListFragment();

        Bundle bundle = new Bundle();
        bundle.putString("menu_id", String.valueOf(menuItem.getMenuItemId()));
        bundle.putString("menu_name", menuItem.getMenuItemName());
        noteListFragment.setArguments(bundle);


        fragmentManager.beginTransaction()
                .replace(R.id.note_container, noteListFragment).commit();
    }

    public void loadAddNoteScreen() {
        AddNoteFragment addNoteFragment = new AddNoteFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.note_container, addNoteFragment).addToBackStack(null).commit();
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
                .replace(R.id.note_container, updateNoteFragment).addToBackStack(null).commit();
    }

    public void loadColorScreen() {
        ColorFragment colorFragment = new ColorFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.note_container, colorFragment).addToBackStack(null).commit();
    }

    /**
     * setUpStatusBar will change the status bar color and the Task Description's
     * icon and bar color.
     * To ensure this feature works, it checks if the api version is 21(LOLLIPOP) or higher.
     *
     * @param colorId - color id coming from getResource().getColor(theme.getPrimaryDarkColor()).
     *                where theme.getPrimaryDarkColor() returns a color from R.color.colorName.
     */

    @Override
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

    public void showBackButton(Boolean enable) {
        if (enable) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            drawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (!toolBarNavigationListenerIsRegistered) {
                drawerToggle.setToolbarNavigationClickListener(v -> onBackPressed());
            }

            toolBarNavigationListenerIsRegistered = true;
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.setToolbarNavigationClickListener(null);
            toolBarNavigationListenerIsRegistered = false;
            fragmentManager.popBackStack();
        }
    }

    private void setUpNavigationView() {
        navigationView.setItemIconTintList(null);
    }

    private void setBurgerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void setAdapter() {
        itemAdapter = new ItemAdapter(this);
        expandableAdapter = new CategoryAdapter(new ArrayList<>(), this);
    }

    private void setRecyclerView() {
        singleRv.setLayoutManager(new LinearLayoutManager(this));
        singleRv.setAdapter(itemAdapter);
        expandableRv.setLayoutManager(new LinearLayoutManager(this));
        expandableRv.setAdapter(expandableAdapter);
    }

    @Override
    public void onTagClick(Tag tag) {
        loadNoteScreen(tag);
    }

    @Override
    public void onMenuItemClick(MenuItem menuItem) {
        loadNoteScreen(menuItem);
    }
}
