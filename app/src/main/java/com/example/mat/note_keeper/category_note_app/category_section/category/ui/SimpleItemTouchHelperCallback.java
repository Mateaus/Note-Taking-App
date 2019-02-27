package com.example.mat.note_keeper.category_note_app.category_section.category.ui;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.category_note_app.category_section.category.CategoryViewModel;
import com.example.mat.note_keeper.category_note_app.category_section.category.adapters.CategoryAdapter;

import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;


public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private CategoryViewModel categoryViewModel;
    private CategoryAdapter categoryAdapter;
    private Fragment fragment;

    public SimpleItemTouchHelperCallback(CategoryViewModel categoryViewModel,
                                         CategoryAdapter categoryAdapter,
                                         Fragment fragment) {
        this.categoryViewModel = categoryViewModel;
        this.categoryAdapter = categoryAdapter;
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
        categoryViewModel.delete(categoryAdapter.getCategoryAt(viewHolder.getAdapterPosition()));
        Toast.makeText(fragment.getActivity(), R.string.category_deleted, Toast.LENGTH_SHORT).show();
    }
}
