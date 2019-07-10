package com.example.mat.roomdb_mvvm.mainactivity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.example.mat.roomdb_mvvm.expandablerecyclerview.models.ExpandableGroup;
import com.example.mat.roomdb_mvvm.mainactivity.adapter.viewholder.TagEditViewHolder;
import com.example.mat.roomdb_mvvm.mainactivity.adapter.viewholder.CategoryViewHolder;
import com.example.mat.roomdb_mvvm.mainactivity.entity.TagCategory;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnMenuItemClickListener;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnTagCategoryEditClickListener;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;

import java.util.List;

public class CategoryEditAdapter extends ExpandableRecyclerViewAdapter<CategoryViewHolder, TagEditViewHolder> {

    private OnMenuItemClickListener onMenuItemClickListener;
    private OnTagCategoryEditClickListener onTagCategoryEditClickListener;

    public CategoryEditAdapter(List<? extends ExpandableGroup> groups, OnMenuItemClickListener onMenuItemClickListener,
                               OnTagCategoryEditClickListener onTagCategoryEditClickListener) {
        super(groups);
        this.onMenuItemClickListener = onMenuItemClickListener;
        this.onTagCategoryEditClickListener = onTagCategoryEditClickListener;
    }

    @Override
    public CategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public TagEditViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_edit_item, parent, false);
        return new TagEditViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(TagEditViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final DrawerMenuItem drawerMenuItem = ((TagCategory) group).getItems().get(childIndex);
        holder.setUI(drawerMenuItem);
        holder.onTagClickListener(drawerMenuItem, onMenuItemClickListener, childIndex);
        holder.onDeleteClickListener(drawerMenuItem, onMenuItemClickListener, childIndex);
    }

    @Override
    public void onBindGroupViewHolder(CategoryViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setEditTagClickListener(group, onTagCategoryEditClickListener);
        holder.setCategoryTag(group);
    }

    public void setTagList(List<DrawerMenuItem> tagList) {
        getGroups().get(0).setItems(tagList);
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        getGroups().get(0).getItems().remove(position);
    }
}
