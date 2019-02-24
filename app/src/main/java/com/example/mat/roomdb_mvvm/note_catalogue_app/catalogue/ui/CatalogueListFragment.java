package com.example.mat.roomdb_mvvm.note_catalogue_app.catalogue.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mat.roomdb_mvvm.MainActivity;
import com.example.mat.roomdb_mvvm.note_catalogue_app.addcatalogue.AddCatalogueDialogFragment;
import com.example.mat.roomdb_mvvm.note_catalogue_app.catalogue.CatalogueViewModel;
import com.example.mat.roomdb_mvvm.note_catalogue_app.catalogue.adapters.CatalogueAdapter;
import com.example.mat.roomdb_mvvm.note_catalogue_app.catalogue.entity.Catalogue;
import com.example.mat.roomdb_mvvm.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class CatalogueListFragment extends Fragment implements OnCatalogueClickListener {

    public static final int ADD_CATALOGUE_REQUEST = 1;

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

        if(requestCode == ADD_CATALOGUE_REQUEST && resultCode == RESULT_OK) {
            this.catalogueViewModel.insert(new Catalogue(data.getStringExtra(AddCatalogueDialogFragment.EXTRA_NAME),
                    data.getStringExtra(AddCatalogueDialogFragment.EXTRA_DESCRIPTION)));
        }
    }

    @Override
    public void onItemClick(Catalogue catalogue) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadNoteScreen(catalogue);
    }

    private void setUpCatalogueAdapter() {
        this.catalogueAdapter = new CatalogueAdapter(this);
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(catalogueAdapter);
    }

    private void eraseCurrentDatabase() {
        getContext().deleteDatabase("note_database");
    }
}
