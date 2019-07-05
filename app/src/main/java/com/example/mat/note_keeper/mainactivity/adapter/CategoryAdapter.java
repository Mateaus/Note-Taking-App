package com.example.mat.note_keeper.mainactivity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter;
import com.example.mat.note_keeper.expandablerecyclerview.models.ExpandableGroup;
import com.example.mat.note_keeper.expandablerecyclerview.viewholders.ChildViewHolder;
import com.example.mat.note_keeper.mainactivity.adapter.viewholder.TagFooterViewHolder;
import com.example.mat.note_keeper.mainactivity.adapter.viewholder.TagListViewHolder;
import com.example.mat.note_keeper.mainactivity.adapter.viewholder.TagsViewHolder;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;
import com.example.mat.note_keeper.mainactivity.listener.OnNewTagClickListener;
import com.example.mat.note_keeper.mainactivity.listener.OnTagCategoryEditClickListener;
import com.example.mat.note_keeper.mainactivity.listener.OnTagClickListener;
import com.example.mat.note_keeper.mainactivity.model.DrawerLayoutMenuItem;

import java.util.List;

public class CategoryAdapter extends MultiTypeExpandableRecyclerViewAdapter<TagListViewHolder, ChildViewHolder> {

    public static final int TAG_VIEW_TYPE = 3;
    public static final int TAG_FOOTER_VIEW_TYPE = 4;
    private int footerButtonColor = R.color.darkbrown;

    private OnTagClickListener onTagClickListener;
    private OnNewTagClickListener onNewTagClickListener;
    private OnTagCategoryEditClickListener onTagCategoryEditClickListener;

    public CategoryAdapter(List<? extends ExpandableGroup> groups, OnTagClickListener onTagClickListener,
                           OnNewTagClickListener onNewTagClickListener, OnTagCategoryEditClickListener onTagCategoryEditClickListener) {
        super(groups);
        this.onTagClickListener = onTagClickListener;
        this.onNewTagClickListener = onNewTagClickListener;
        this.onTagCategoryEditClickListener = onTagCategoryEditClickListener;
    }

    @Override
    public TagListViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_category_item, parent, false);
        return new TagListViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TAG_VIEW_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item, parent, false);
                return new TagsViewHolder(view);
            case TAG_FOOTER_VIEW_TYPE:
                View footer = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_footer_item, parent, false);
                return new TagFooterViewHolder(footer);
            default:
                throw new IllegalArgumentException("Invalid viewType");
        }
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        int viewType = getItemViewType(flatPosition);
        switch (viewType) {
            case TAG_VIEW_TYPE:
                final DrawerLayoutMenuItem tag = ((TagCategory) group).getItems().get(childIndex);
                ((TagsViewHolder) holder).onTagClickListener(tag, onTagClickListener);
                ((TagsViewHolder) holder).bind(tag);
                break;
            case TAG_FOOTER_VIEW_TYPE:
                ((TagFooterViewHolder) holder).setNewTagClickListener((TagCategory) getGroups().get(0), onNewTagClickListener);
                ((TagFooterViewHolder) holder).setFooter("New Tag", footerButtonColor);
        }
    }

    @Override
    public void onBindGroupViewHolder(TagListViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setEditTagClickListener(group, onTagCategoryEditClickListener);
        holder.setCategoryTag(group);
    }

    @Override
    public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
        if (position > ((TagCategory) group).getItems().size()) {
            return TAG_FOOTER_VIEW_TYPE;
        } else {
            return TAG_VIEW_TYPE;
        }
    }

    @Override
    public boolean isChild(int viewType) {
        return viewType == TAG_VIEW_TYPE || viewType == TAG_FOOTER_VIEW_TYPE;
    }

    public void setTagCategories(List<? extends ExpandableGroup> tagCategories) {
        // If the group is empty, fill it with the new incoming tag categories
        if (getGroups().size() == 0) {
            setGroups(tagCategories);
            notifyGroupDataChanged();
            notifyDataSetChanged();
        } else { // Else, just populate the items inside that existing category without recreating it.
            TagCategory tagCategory = (TagCategory) getGroups().get(0);
            tagCategory.setItems(tagCategories.get(0).getItems());
            notifyDataSetChanged();
        }
    }

    public void setTagList(List<DrawerLayoutMenuItem> tagList) {
        getGroups().get(0).setItems(tagList);
        notifyDataSetChanged();
    }

    public void updateFooterButtonColor(int color) {
        this.footerButtonColor = color;
        notifyDataSetChanged();
    }
}
