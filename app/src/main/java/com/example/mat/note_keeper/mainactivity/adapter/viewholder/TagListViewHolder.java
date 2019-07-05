package com.example.mat.note_keeper.mainactivity.adapter.viewholder;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.expandablerecyclerview.models.ExpandableGroup;
import com.example.mat.note_keeper.expandablerecyclerview.viewholders.GroupViewHolder;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;
import com.example.mat.note_keeper.mainactivity.listener.OnTagCategoryEditClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class TagListViewHolder extends GroupViewHolder {

    @BindView(R.id.tv_tag_category)
    TextView categoryTag;

    @BindView(R.id.b_tag_edit_category)
    Button categoryTagBtn;

    public TagListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setCategoryTag(ExpandableGroup group) {
        categoryTag.setText(group.getTitle());
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
        categoryTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTagCategoryEditClickListener.onTagEditClickListener(group);
            }
        });
    }
}
