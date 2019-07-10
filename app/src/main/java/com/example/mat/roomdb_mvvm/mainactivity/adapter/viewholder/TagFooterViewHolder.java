package com.example.mat.roomdb_mvvm.mainactivity.adapter.viewholder;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.mat.roomdb_mvvm.databinding.TagFooterItemBinding;
import com.example.mat.roomdb_mvvm.expandablerecyclerview.viewholders.ChildViewHolder;
import com.example.mat.roomdb_mvvm.mainactivity.entity.TagCategory;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnNewTagClickListener;

public class TagFooterViewHolder extends ChildViewHolder {

    private TagFooterItemBinding viewBinding;

    public TagFooterViewHolder(View itemView) {
        super(itemView);
        viewBinding = DataBindingUtil.bind(itemView);
    }

    public void setFooter(String footer, int color) {
        viewBinding.tagFooterItemBtn.setText(footer);
        viewBinding.tagFooterItemBtn.setBackgroundTintList(
                viewBinding.getRoot().getContext().getResources().getColorStateList(color));
    }

    public void setNewTagClickListener(final TagCategory tagCategory,
                                       final OnNewTagClickListener onNewTagClickListener) {

        viewBinding.tagFooterItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewTagClickListener.onNewTagClick(tagCategory);
            }
        });
    }
}
