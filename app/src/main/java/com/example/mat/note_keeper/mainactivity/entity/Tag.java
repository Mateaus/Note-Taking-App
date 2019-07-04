package com.example.mat.note_keeper.mainactivity.entity;

import androidx.room.Ignore;

import com.example.mat.note_keeper.mainactivity.model.MenuItem;

public class Tag extends MenuItem {

    @Ignore
    public Tag(String menuItemName, int menuItemSize) {
        super(menuItemName, menuItemSize);
    }

    public Tag(int menuItemId, String menuItemName, int menuItemSize) {
        super(menuItemId, menuItemName, menuItemSize);
    }

    @Override
    public String toString() {
        return getMenuItemName();
    }
}
