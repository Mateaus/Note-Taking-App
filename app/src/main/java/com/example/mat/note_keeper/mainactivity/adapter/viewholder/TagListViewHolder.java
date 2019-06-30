package com.example.mat.note_keeper.mainactivity.adapter.viewholder;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.expandablerecyclerview.models.ExpandableGroup;
import com.example.mat.note_keeper.expandablerecyclerview.vieholders.GroupViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class TagListViewHolder extends GroupViewHolder {

    @BindView(R.id.tv_tag_category)
    TextView categoryTag;

    @BindView(R.id.iv_arrow_expand)
    ImageView mArrowExpandImageView;

    int initialCollapseCtn = 0;

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
        animateExpand();
    }

    @Override
    public void collapse() {
        super.collapse();
        if (initialCollapseCtn >= 1) {
            animateCollapse();
        }
        initialCollapseCtn++;
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        mArrowExpandImageView.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        mArrowExpandImageView.setAnimation(rotate);
    }
}
