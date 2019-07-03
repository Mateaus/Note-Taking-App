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
import com.example.mat.note_keeper.mainactivity.entity.Tag;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;
import com.example.mat.note_keeper.mainactivity.listener.OnTagClickListener;
import com.example.mat.note_keeper.mainactivity.listener.OnNewTagClickListener;

import java.util.List;

public class CategoryAdapter extends MultiTypeExpandableRecyclerViewAdapter<TagListViewHolder, ChildViewHolder> {

    public static final int TAG_VIEW_TYPE = 3;
    public static final int TAG_FOOTER_VIEW_TYPE = 4;
    private int footerButtonColor = R.color.darkbrown;

    private OnTagClickListener onTagClickListener;
    private OnNewTagClickListener onNewTagClickListener;

    public CategoryAdapter(List<? extends ExpandableGroup> groups, OnTagClickListener onTagClickListener,
                           OnNewTagClickListener onNewTagClickListener) {
        super(groups);
        this.onTagClickListener = onTagClickListener;
        this.onNewTagClickListener = onNewTagClickListener;
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
                final Tag tag = ((TagCategory) group).getItems().get(childIndex);
                ((TagsViewHolder)holder).onTagClickListener(tag, onTagClickListener);
                ((TagsViewHolder)holder).bind(tag);
                break;
            case TAG_FOOTER_VIEW_TYPE:
                ((TagFooterViewHolder)holder).setNewTagClickListener(onNewTagClickListener);
                ((TagFooterViewHolder)holder).setFooter("New Tag", footerButtonColor);
        }
    }

    @Override
    public void onBindGroupViewHolder(TagListViewHolder holder, int flatPosition, ExpandableGroup group) {
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
        setGroups(tagCategories);
        notifyGroupDataChanged();
        notifyDataSetChanged();
    }

    public void updateFooterButtonColor(int color) {
        this.footerButtonColor = color;
        notifyDataSetChanged();
    }
}
