package com.example.mat.note_keeper.mainactivity.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "menu_item")
public class MenuItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "menu_item_id")
    private int menuItemId;

    @ColumnInfo(name = "menu_item_name")
    private String menuItemName;

    @ColumnInfo(name = "menu_item_size")
    private int menuItemSize;

    @Ignore
    public MenuItem(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    @Ignore
    public MenuItem(String menuItemName, int menuItemSize) {
        this.menuItemName = menuItemName;
        this.menuItemSize = menuItemSize;
    }

    public MenuItem(int menuItemId, String menuItemName, int menuItemSize) {
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.menuItemSize = menuItemSize;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public int getMenuItemSize() {
        return menuItemSize;
    }

    public void setMenuItemSize(int menuItemSize) {
        this.menuItemSize = menuItemSize;
    }
}
