package com.example.mat.roomdb_mvvm.mainactivity.entity;

import java.util.List;

public class TagEditCategory extends TagCategory {

    public TagEditCategory(String title, String option, List<Tag> items) {
        super(title, option, items);
    }

    @Override
    public int getItemCount() {
        return getItems() == null ? 0 : getItems().size();
    }
}
