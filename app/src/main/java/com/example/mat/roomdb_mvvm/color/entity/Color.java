package com.example.mat.roomdb_mvvm.color.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "color_table")
public class Color {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "status_bar_color")
    private int statusBarColor;

    @ColumnInfo(name = "tool_bar_color")
    private int toolBarColor;

    @ColumnInfo(name = "tool_bar_title_color")
    private int toolBarTitleColor;

    @ColumnInfo(name = "menu_icon_color")
    private int menuIconColor;

    @ColumnInfo(name = "card_color")
    private int cardColor;

    @ColumnInfo(name = "card_title_color")
    private int cardTitleColor;

    @ColumnInfo(name = "card_description_color")
    private int cardDescriptionColor;

    @ColumnInfo(name = "card_date_color")
    private int cardDateColor;

    @ColumnInfo(name = "body_background_color")
    private int bodyBackgroundColor;

    @ColumnInfo(name = "add_button_icon_color")
    private int addButtonIconColor;

    @ColumnInfo(name = "add_button_background_color")
    private int addButtonBackgroundColor;

    public Color(int statusBarColor, int toolBarColor,
                 int toolBarTitleColor, int menuIconColor,
                 int cardColor, int cardTitleColor,
                 int cardDescriptionColor, int cardDateColor,
                 int bodyBackgroundColor, int addButtonIconColor,
                 int addButtonBackgroundColor) {
        this.statusBarColor = statusBarColor;
        this.toolBarColor = toolBarColor;
        this.toolBarTitleColor = toolBarTitleColor;
        this.menuIconColor = menuIconColor;
        this.cardColor = cardColor;
        this.cardTitleColor = cardTitleColor;
        this.cardDescriptionColor = cardDescriptionColor;
        this.cardDateColor = cardDateColor;
        this.bodyBackgroundColor = bodyBackgroundColor;
        this.addButtonIconColor = addButtonIconColor;
        this.addButtonBackgroundColor = addButtonBackgroundColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatusBarColor() {
        return statusBarColor;
    }

    public void setStatusBarColor(int statusBarColor) {
        this.statusBarColor = statusBarColor;
    }

    public int getToolBarColor() {
        return toolBarColor;
    }

    public void setToolBarColor(int toolBarColor) {
        this.toolBarColor = toolBarColor;
    }

    public int getToolBarTitleColor() {
        return toolBarTitleColor;
    }

    public void setToolBarTitleColor(int toolBarTitleColor) {
        this.toolBarTitleColor = toolBarTitleColor;
    }

    public int getMenuIconColor() {
        return menuIconColor;
    }

    public void setMenuIconColor(int menuIconColor) {
        this.menuIconColor = menuIconColor;
    }

    public int getCardColor() {
        return cardColor;
    }

    public void setCardColor(int cardColor) {
        this.cardColor = cardColor;
    }

    public int getCardTitleColor() {
        return cardTitleColor;
    }

    public void setCardTitleColor(int cardTitleColor) {
        this.cardTitleColor = cardTitleColor;
    }

    public int getCardDescriptionColor() {
        return cardDescriptionColor;
    }

    public void setCardDescriptionColor(int cardDescriptionColor) {
        this.cardDescriptionColor = cardDescriptionColor;
    }

    public int getCardDateColor() {
        return cardDateColor;
    }

    public void setCardDateColor(int cardDateColor) {
        this.cardDateColor = cardDateColor;
    }

    public int getBodyBackgroundColor() {
        return bodyBackgroundColor;
    }

    public void setBodyBackgroundColor(int bodyBackgroundColor) {
        this.bodyBackgroundColor = bodyBackgroundColor;
    }

    public int getAddButtonIconColor() {
        return addButtonIconColor;
    }

    public void setAddButtonIconColor(int addButtonIconColor) {
        this.addButtonIconColor = addButtonIconColor;
    }

    public int getAddButtonBackgroundColor() {
        return addButtonBackgroundColor;
    }

    public void setAddButtonBackgroundColor(int addButtonBackgroundColor) {
        this.addButtonBackgroundColor = addButtonBackgroundColor;
    }
}
