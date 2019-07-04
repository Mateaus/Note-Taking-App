package com.example.mat.note_keeper.mainactivity.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.mat.note_keeper.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

@Entity(tableName = "tag_category")
public class TagCategory extends ExpandableGroup<Tag> {

    public TagCategory(String title, List<Tag> items) {
        super(title, items);
    }
}
