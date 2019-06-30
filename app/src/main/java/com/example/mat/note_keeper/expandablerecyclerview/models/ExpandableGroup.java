package com.example.mat.note_keeper.expandablerecyclerview.models;

import androidx.room.TypeConverters;

import com.example.mat.note_keeper.dataconverters.TagDataConverter;

import java.util.List;

public class ExpandableGroup<T> {

    private String title;

    @TypeConverters(TagDataConverter.class)
    private List<T> items;

    public ExpandableGroup(String title, List<T> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public List<T> getItems() {
        return items;
    }

    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public String toString() {
        return "ExpandableGroup{" +
                "title='" + title + '\'' +
                ", items=" + items +
                '}';
    }
}
