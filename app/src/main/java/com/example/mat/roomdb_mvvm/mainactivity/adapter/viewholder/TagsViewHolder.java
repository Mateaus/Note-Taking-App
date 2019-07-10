package com.example.mat.roomdb_mvvm.mainactivity.adapter.viewholder;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.expandablerecyclerview.viewholders.ChildViewHolder;
import com.example.mat.roomdb_mvvm.mainactivity.listener.OnTagClickListener;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagsViewHolder extends ChildViewHolder {

    @BindView(R.id.tag_name_tv)
    public TextView mTagTextView;

    @BindView(R.id.tag_size_tv)
    public TextView mTagSizeTextView;

    @BindView(R.id.tag_icon_iv)
    public ImageView mTagIconImageView;

    private View view;

    public TagsViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void bind(DrawerMenuItem tag) {
        mTagTextView.setText(tag.getMenuItemName());
        mTagSizeTextView.setText(String.valueOf(tag.getMenuItemSize()));

        Resources resources = view.getResources();
        Integer menuIcon = resources.getIdentifier(tag.getMenuItemImage(), "drawable",
                view.getContext().getPackageName());
        mTagIconImageView.setImageResource(menuIcon);
    }

    public void onTagClickListener(final DrawerMenuItem drawerMenuItem,
                                   final OnTagClickListener onTagClickListener) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTagClickListener.onTagClick(drawerMenuItem);
            }
        });
    }

}
