package com.example.mat.note_keeper.mainactivity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.example.mat.note_keeper.expandablerecyclerview.models.ExpandableGroup;
import com.example.mat.note_keeper.mainactivity.adapter.viewholder.TagListViewHolder;
import com.example.mat.note_keeper.mainactivity.adapter.viewholder.TagsViewHolder;
import com.example.mat.note_keeper.mainactivity.entity.Tag;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;
import com.example.mat.note_keeper.mainactivity.listener.OnTagClickListener;

import java.util.List;

public class CategoryAdapter extends ExpandableRecyclerViewAdapter<TagListViewHolder, TagsViewHolder> {

    private OnTagClickListener onTagClickListener;

    public CategoryAdapter(List<? extends ExpandableGroup> groups, OnTagClickListener onTagClickListener) {
        super(groups);
        this.onTagClickListener = onTagClickListener;
    }

    @Override
    public TagListViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_category_item, parent, false);
        return new TagListViewHolder(view);
    }

    @Override
    public TagsViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item, parent, false);
        return new TagsViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(TagsViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Tag tag = ((TagCategory) group).getItems().get(childIndex);
        holder.onTagClickListener(tag, onTagClickListener);
        holder.bind(tag);
    }

    @Override
    public void onBindGroupViewHolder(TagListViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setCategoryTag(group);
    }

    public void setTagCategories(List<? extends ExpandableGroup> tagCategories) {
        setGroups(tagCategories);
        notifyGroupDataChanged();
        notifyDataSetChanged();
    }
}
