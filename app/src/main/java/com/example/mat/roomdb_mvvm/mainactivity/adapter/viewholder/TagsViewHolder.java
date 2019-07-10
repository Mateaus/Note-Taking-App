package com.example.mat.roomdb_mvvm.mainactivity.adapter.viewholder;

import android.content.res.Resources;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.mat.roomdb_mvvm.databinding.TagItemBinding;
import com.example.mat.roomdb_mvvm.expandablerecyclerview.viewholders.ChildViewHolder;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnTagClickListener;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;

public class TagsViewHolder extends ChildViewHolder {

    private TagItemBinding viewBinding;

    public TagsViewHolder(View itemView) {
        super(itemView);
        viewBinding = DataBindingUtil.bind(itemView);
    }

    public void bind(DrawerMenuItem tag) {
        viewBinding.tagItemNameTv.setText(tag.getMenuItemName());
        viewBinding.tagItemSizeTv.setText(String.valueOf(tag.getMenuItemSize()));

        Resources resources = viewBinding.getRoot().getResources();
        Integer menuIcon = resources.getIdentifier(tag.getMenuItemImage(), "drawable",
                viewBinding.getRoot().getContext().getPackageName());
        viewBinding.tagItemIconIv.setImageResource(menuIcon);
    }

    public void onTagClickListener(final DrawerMenuItem drawerMenuItem,
                                   final OnTagClickListener onTagClickListener) {
        viewBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTagClickListener.onTagClick(drawerMenuItem);
            }
        });
    }

}
