package com.example.mat.note_keeper.mainactivity.entity;

import androidx.room.Ignore;

import com.example.mat.note_keeper.mainactivity.model.DrawerLayoutMenuItem;

public class Tag extends DrawerLayoutMenuItem {

    @Ignore
    public Tag(String menuItemName, int menuItemSize, String menuItemIcon) {
        super(menuItemName, menuItemSize, menuItemIcon);
    }

    public Tag(int menuItemId, String menuItemName, int menuItemSize, String menuItemIcon) {
        super(menuItemId, menuItemName, menuItemSize, menuItemIcon);
    }

    @Override
    public String toString() {
        return getMenuItemName();
    }
}
