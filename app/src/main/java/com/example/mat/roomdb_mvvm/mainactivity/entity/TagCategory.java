package com.example.mat.roomdb_mvvm.mainactivity.entity;

import androidx.room.Entity;

import com.example.mat.roomdb_mvvm.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

@Entity(tableName = "tag_category")
public class TagCategory extends ExpandableGroup<Tag> {

    public TagCategory(String title, List<Tag> items) {
        super(title, items);
    }
}
