package com.example.mat.roomdb_mvvm.expandablerecyclerview.models;

import java.util.List;

public class ExpandableGroup<T> {

    private String title;
    private String option;
    private List<T> items;

    public ExpandableGroup(String title, String option, List<T> items) {
        this.title = title;
        this.items = items;
        this.option = option;
    }

    public String getTitle() {
        return title;
    }

    public String getOption() {
        return option;
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
