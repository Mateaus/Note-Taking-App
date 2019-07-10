package com.example.mat.roomdb_mvvm.mainactivity.entity;

import androidx.room.Ignore;

import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;

public class Tag extends DrawerMenuItem {

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
