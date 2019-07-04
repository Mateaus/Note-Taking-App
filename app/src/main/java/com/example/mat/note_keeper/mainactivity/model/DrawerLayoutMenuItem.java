package com.example.mat.note_keeper.mainactivity.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "menu_item")
public class DrawerLayoutMenuItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "menu_item_id")
    private int menuItemId;

    @ColumnInfo(name = "menu_item_name")
    private String menuItemName;

    @ColumnInfo(name = "menu_item_size")
    private int menuItemSize;

    @ColumnInfo(name = "menu_item_image")
    private String menuItemImage;

    @Ignore
    public DrawerLayoutMenuItem(String menuItemName, int menuItemSize, String menuItemImage) {
        this.menuItemName = menuItemName;
        this.menuItemSize = menuItemSize;
        this.menuItemImage = menuItemImage;
    }

    public DrawerLayoutMenuItem(int menuItemId, String menuItemName, int menuItemSize, String menuItemImage) {
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.menuItemSize = menuItemSize;
        this.menuItemImage = menuItemImage;
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

    public String getMenuItemImage() {
        return menuItemImage;
    }

    public void setMenuItemImage(String menuItemImage) {
        this.menuItemImage = menuItemImage;
    }

    @NonNull
    @Override
    public String toString() {
        return getMenuItemName();
    }
}
