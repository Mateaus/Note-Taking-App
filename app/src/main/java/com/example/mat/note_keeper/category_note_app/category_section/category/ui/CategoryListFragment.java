package com.example.mat.note_keeper.category_note_app.category_section.category.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mat.note_keeper.MainActivity;
import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.category_note_app.category_section.addcategory.AddCategoryDialogFragment;
import com.example.mat.note_keeper.category_note_app.category_section.category.CategoryViewModel;
import com.example.mat.note_keeper.category_note_app.category_section.category.adapters.CategoryAdapter;
import com.example.mat.note_keeper.category_note_app.category_section.category.entity.Category;
import com.example.mat.note_keeper.category_note_app.category_section.updatecategory.UpdateCategoryFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class CategoryListFragment extends Fragment implements OnCategoryClickListener {

    public static final int ADD_CATEGORY_REQUEST = 1;
    public static final int UPDATE_CATEGORY_REQUEST = 2;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.add_category_btn)
    FloatingActionButton addBtn;

    private CategoryViewModel categoryViewModel;
    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category_list, container, false);
        ButterKnife.bind(this, v);

        setUpToolBar();
        setUpCategoryAdapter();
        setUpRecyclerView();

        //eraseCurrentDatabase();

        this.categoryViewModel = ViewModelProviders.of(this.getActivity()).get(CategoryViewModel.class);
        this.categoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                categoryAdapter.submitList(categories);
            }
        });

        setUpItemTouchHelper();

        return v;
    }

    @OnClick(R.id.add_category_btn)
    public void addButtonHandler() {
        AddCategoryDialogFragment categoryDialogFragment = new AddCategoryDialogFragment();
        categoryDialogFragment.setTargetFragment(CategoryListFragment.this, ADD_CATEGORY_REQUEST);
        categoryDialogFragment.show(getFragmentManager(), "ADD_CATEGORY");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CATEGORY_REQUEST && resultCode == RESULT_OK) {
            this.categoryViewModel.insert(new Category(data.getStringExtra(AddCategoryDialogFragment.EXTRA_NAME),
                    data.getStringExtra(AddCategoryDialogFragment.EXTRA_DESCRIPTION)));
        }

        if (requestCode == UPDATE_CATEGORY_REQUEST && resultCode == RESULT_OK) {
            int cid = Integer.valueOf(data.getStringExtra(UpdateCategoryFragment.EXTRA_CID));

            Category updateCategory = new Category(
                    data.getStringExtra(UpdateCategoryFragment.EXTRA_CSUBJECT),
                    data.getStringExtra(UpdateCategoryFragment.EXTRA_CDESCRIPTION));
            updateCategory.setC_id(cid);
            this.categoryViewModel.update(updateCategory);
        }
    }

    @Override
    public void onItemClick(Category category) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadNoteScreen(category);
    }

    @Override
    public void onLongItemClick(Category category) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadUpdateCategoryScreen(category, this);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.note_list_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync_data:
                syncData();
                return true;
            case R.id.settings:
                settings();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpCategoryAdapter() {
        this.categoryAdapter = new CategoryAdapter(this);
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(categoryAdapter);
    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(categoryViewModel,
                categoryAdapter, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle(R.string.category_list);
    }

    private void syncData() {
        // TODO: Sync data from current DB into Firebase auth + realtime DB.
    }

    private void settings() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadSettingScreen();
    }

    private void eraseCurrentDatabase() {
        getContext().deleteDatabase("note_database");
    }
}
