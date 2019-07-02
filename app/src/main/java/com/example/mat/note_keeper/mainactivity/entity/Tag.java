package com.example.mat.note_keeper.mainactivity.entity;

import com.example.mat.note_keeper.mainactivity.model.MenuItem;

public class Tag extends MenuItem {

    public Tag(int menuItemId) {
        super(menuItemId);
    }

    public Tag(String menuItemName, int menuItemSize) {
        super(menuItemName, menuItemSize);
    }

    public Tag(int menuItemId, String menuItemName, int menuItemSize) {
        super(menuItemId, menuItemName, menuItemSize);
    }
}
