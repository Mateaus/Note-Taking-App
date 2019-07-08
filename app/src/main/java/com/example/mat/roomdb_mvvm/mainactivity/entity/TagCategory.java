package com.example.mat.roomdb_mvvm.mainactivity.entity;

import com.example.mat.roomdb_mvvm.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class TagCategory extends ExpandableGroup<Tag> {

    public TagCategory(String title, String option, List<Tag> items) {
        super(title, option, items);
    }
}
