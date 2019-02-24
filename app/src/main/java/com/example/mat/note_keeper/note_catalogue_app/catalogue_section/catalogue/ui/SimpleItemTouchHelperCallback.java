package com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.ui;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.CatalogueViewModel;
import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.adapters.CatalogueAdapter;

import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;


public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private CatalogueViewModel catalogueViewModel;
    private CatalogueAdapter catalogueAdapter;
    private Fragment fragment;

    public SimpleItemTouchHelperCallback(CatalogueViewModel catalogueViewModel,
                                         CatalogueAdapter catalogueAdapter,
                                         Fragment fragment) {
        this.catalogueViewModel = catalogueViewModel;
        this.catalogueAdapter = catalogueAdapter;
        this.fragment = fragment;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT|RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        catalogueViewModel.delete(catalogueAdapter.getCatalogueAt(viewHolder.getAdapterPosition()));
        Toast.makeText(fragment.getActivity(), R.string.catalogue_deleted, Toast.LENGTH_SHORT).show();
    }
}
