package com.example.mat.note_keeper.expandablerecyclerview.models;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mat.note_keeper.dataconverters.TagDataConverter;

import java.util.List;

public class ExpandableGroup<T> {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;

    @TypeConverters(TagDataConverter.class)
    private List<T> items;

    @Ignore
    public ExpandableGroup(String title, List<T> items) {
        this.title = title;
        this.items = items;
    }

    public ExpandableGroup(int id, String title, List<T> items) {
        this.id = id;
        this.title = title;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getItemCount() {
        return items == null ? 0 : items.size() + 1;
    }

    @Override
    public String toString() {
        return "ExpandableGroup{" +
                "title='" + title + '\'' +
                ", items=" + items +
                '}';
    }
}
