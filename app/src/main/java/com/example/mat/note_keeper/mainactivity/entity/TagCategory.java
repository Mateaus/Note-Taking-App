package com.example.mat.note_keeper.mainactivity.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mat.note_keeper.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

@Entity(tableName = "tag_category")
public class TagCategory extends ExpandableGroup<Tag> {

    @PrimaryKey(autoGenerate = true)
    private int tagCategoryId;

    public TagCategory(String title, List<Tag> items) {
        super(title, items);
    }

    public int getTagCategoryId() {
        return tagCategoryId;
    }

    public void setTagCategoryId(int tagCategoryId) {
        this.tagCategoryId = tagCategoryId;
    }
}
