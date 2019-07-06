package com.example.mat.roomdb_mvvm.mainactivity.adapter.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.expandablerecyclerview.models.ExpandableGroup;
import com.example.mat.roomdb_mvvm.expandablerecyclerview.viewholders.GroupViewHolder;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnTagCategoryEditClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

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
