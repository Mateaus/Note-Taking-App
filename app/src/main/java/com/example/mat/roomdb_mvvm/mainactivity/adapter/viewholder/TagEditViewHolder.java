package com.example.mat.roomdb_mvvm.mainactivity.adapter.viewholder;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.mat.roomdb_mvvm.databinding.TagEditItemBinding;
import com.example.mat.roomdb_mvvm.expandablerecyclerview.viewholders.ChildViewHolder;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnMenuItemClickListener;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;

public class TagEditViewHolder extends ChildViewHolder {

    private TagEditItemBinding viewBinding;

    public TagEditViewHolder(View itemView) {
        super(itemView);
        viewBinding = DataBindingUtil.bind(itemView);
    }

    public void setUI(DrawerMenuItem drawerMenuItem) {
        viewBinding.tagEditItemNameTv.setText(drawerMenuItem.getMenuItemName());
        if (drawerMenuItem.getMenuItemId() == 3) {
            hideDeleteButton();
        }
    }

    public void onDeleteClickListener(final DrawerMenuItem drawerMenuItem,
                                      final OnMenuItemClickListener onMenuItemClickListener, final int position) {
        viewBinding.tagEditItemDeleteIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuItemClickListener.onMenuDeleteClick(drawerMenuItem, position);
            }
        });
    }

    public void onTagClickListener(final DrawerMenuItem drawerMenuItem,
                                   final OnMenuItemClickListener onMenuItemClickListener, final int position) {
        viewBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuItemClickListener.onMenuUpdateItemClick(drawerMenuItem, position);
            }
        });
    }

    private void hideDeleteButton() {
        viewBinding.tagEditItemDeleteIb.setVisibility(View.GONE);
    }
}
