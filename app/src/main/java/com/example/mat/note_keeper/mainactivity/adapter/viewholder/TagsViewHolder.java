package com.example.mat.note_keeper.mainactivity.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.expandablerecyclerview.vieholders.ChildViewHolder;
import com.example.mat.note_keeper.mainactivity.entity.Tag;
import com.example.mat.note_keeper.mainactivity.listener.OnTagClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagsViewHolder extends ChildViewHolder {

    @BindView(R.id.tag_name_tv)
    public TextView mTagTextView;

    @BindView(R.id.tag_size_tv)
    public TextView mTagSizeTextView;

    private View view;

    public TagsViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void bind(Tag tag) {
        mTagTextView.setText(tag.getMenuItemName());
        mTagSizeTextView.setText(String.valueOf(tag.getMenuItemSize()));
    }

    public void onTagClickListener(final Tag tag,
                                   final OnTagClickListener onTagClickListener) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTagClickListener.onTagClick(tag);
            }
        });
    }

}
