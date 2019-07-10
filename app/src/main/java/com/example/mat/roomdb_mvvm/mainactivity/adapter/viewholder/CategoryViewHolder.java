package com.example.mat.roomdb_mvvm.mainactivity.adapter.viewholder;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.mat.roomdb_mvvm.databinding.TagCategoryItemBinding;
import com.example.mat.roomdb_mvvm.expandablerecyclerview.models.ExpandableGroup;
import com.example.mat.roomdb_mvvm.expandablerecyclerview.viewholders.GroupViewHolder;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnTagCategoryEditClickListener;

public class CategoryViewHolder extends GroupViewHolder {

    private TagCategoryItemBinding viewBinding;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        viewBinding = DataBindingUtil.bind(itemView);
    }

    public void setCategoryTag(ExpandableGroup group) {
        viewBinding.tagCategoryItemNameTv.setText(group.getTitle());
        viewBinding.tagCategoryItemEditB.setText(group.getOption());
    }

    @Override
    public void expand() {
        super.expand();
    }

    @Override
    public void collapse() {
        super.collapse();
    }

    public void setEditTagClickListener(final ExpandableGroup group,
                                        final OnTagCategoryEditClickListener onTagCategoryEditClickListener) {
        viewBinding.tagCategoryItemEditB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTagCategoryEditClickListener.onTagEditClickListener(group);
            }
        });
    }
}
