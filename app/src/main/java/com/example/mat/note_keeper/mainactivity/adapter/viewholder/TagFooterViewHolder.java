package com.example.mat.note_keeper.mainactivity.adapter.viewholder;

import android.view.View;
import android.widget.Button;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.expandablerecyclerview.models.ExpandableGroup;
import com.example.mat.note_keeper.expandablerecyclerview.viewholders.ChildViewHolder;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;
import com.example.mat.note_keeper.mainactivity.listener.OnNewTagClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagFooterViewHolder extends ChildViewHolder {

    @BindView(R.id.footerB)
    Button footerB;

    private View view;

    public TagFooterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.view = itemView;
    }

    public void setFooter(String footer, int color) {
        footerB.setText(footer);
        footerB.setBackgroundTintList(view.getContext().getResources().getColorStateList(color));
    }

    public void setNewTagClickListener(final TagCategory tagCategory,
                                       final OnNewTagClickListener onNewTagClickListener) {

        footerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewTagClickListener.onNewTagClick(tagCategory);
            }
        });
    }
}
