package com.example.mat.roomdb_mvvm.mainactivity.adapter.viewholder;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.mat.roomdb_mvvm.databinding.TagEditItemBinding;
import com.example.mat.roomdb_mvvm.expandablerecyclerview.viewholders.ChildViewHolder;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnMenuItemClickListener;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerLayoutMenuItem;

public class TagEditViewHolder extends ChildViewHolder {

    private TagEditItemBinding tagEditItemBinding;

    public TagEditViewHolder(View itemView) {
        super(itemView);
        tagEditItemBinding = DataBindingUtil.bind(itemView);
    }

    public void setUI(DrawerLayoutMenuItem drawerLayoutMenuItem) {
        tagEditItemBinding.tvTagName.setText(drawerLayoutMenuItem.getMenuItemName());
        if (drawerLayoutMenuItem.getMenuItemId() == 3) {
            hideDeleteButton();
        }
    }

    public void onDeleteClickListener(final DrawerLayoutMenuItem drawerLayoutMenuItem,
                                      final OnMenuItemClickListener onMenuItemClickListener, int position) {
        tagEditItemBinding.ibEditTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuItemClickListener.onMenuDeleteClick(drawerLayoutMenuItem, position);
            }
        });
    }

    public void onTagClickListener(final DrawerLayoutMenuItem drawerLayoutMenuItem,
                                   final OnMenuItemClickListener onMenuItemClickListener, int position) {
        tagEditItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuItemClickListener.onMenuUpdateItemClick(drawerLayoutMenuItem, position);
            }
        });
    }

    private void hideDeleteButton() {
        tagEditItemBinding.ibEditTag.setVisibility(View.GONE);
    }
}
