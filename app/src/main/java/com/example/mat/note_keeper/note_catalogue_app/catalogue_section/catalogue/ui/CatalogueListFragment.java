package com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.ui;

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
import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.addcatalogue.AddCatalogueDialogFragment;
import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.CatalogueViewModel;
import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.adapters.CatalogueAdapter;
import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.entity.Catalogue;
import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.updatecatalogue.UpdateCatalogueFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class CatalogueListFragment extends Fragment implements OnCatalogueClickListener {

    public static final int ADD_CATALOGUE_REQUEST = 1;
    public static final int UPDATE_CATALOGUE_REQUEST = 2;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.add_catalogue_btn)
    FloatingActionButton addBtn;

    private CatalogueViewModel catalogueViewModel;
    private CatalogueAdapter catalogueAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_catalogue_list, container, false);
        ButterKnife.bind(this, v);

        setUpToolBar();
        setUpCatalogueAdapter();
        setUpRecyclerView();

        //eraseCurrentDatabase();

        this.catalogueViewModel = ViewModelProviders.of(this.getActivity()).get(CatalogueViewModel.class);
        this.catalogueViewModel.getAllCatalogues().observe(this, new Observer<List<Catalogue>>() {
            @Override
            public void onChanged(@Nullable List<Catalogue> catalogues) {
                catalogueAdapter.submitList(catalogues);
            }
        });

        setUpItemTouchHelper();

        return v;
    }

    @OnClick(R.id.add_catalogue_btn)
    public void addButtonHandler() {
        AddCatalogueDialogFragment catalogueDialogFragment = new AddCatalogueDialogFragment();
        catalogueDialogFragment.setTargetFragment(CatalogueListFragment.this, ADD_CATALOGUE_REQUEST);
        catalogueDialogFragment.show(getFragmentManager(), "ADD_CATALOGUE");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CATALOGUE_REQUEST && resultCode == RESULT_OK) {
            this.catalogueViewModel.insert(new Catalogue(data.getStringExtra(AddCatalogueDialogFragment.EXTRA_NAME),
                    data.getStringExtra(AddCatalogueDialogFragment.EXTRA_DESCRIPTION)));
        }

        if (requestCode == UPDATE_CATALOGUE_REQUEST && resultCode == RESULT_OK) {
            int cid = Integer.valueOf(data.getStringExtra(UpdateCatalogueFragment.EXTRA_CID));

            Catalogue updateCatalogue = new Catalogue(
                    data.getStringExtra(UpdateCatalogueFragment.EXTRA_CSUBJECT),
                    data.getStringExtra(UpdateCatalogueFragment.EXTRA_CDESCRIPTION));
            updateCatalogue.setC_id(cid);
            this.catalogueViewModel.update(updateCatalogue);
        }
    }

    @Override
    public void onItemClick(Catalogue catalogue) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadNoteScreen(catalogue);
    }

    @Override
    public void onLongItemClick(Catalogue catalogue) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadUpdateCatalogueScreen(catalogue, this);
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

    private void setUpCatalogueAdapter() {
        this.catalogueAdapter = new CatalogueAdapter(this);
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(catalogueAdapter);
    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(catalogueViewModel,
                catalogueAdapter, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setUpToolBar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle(R.string.catalogue_list);
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
