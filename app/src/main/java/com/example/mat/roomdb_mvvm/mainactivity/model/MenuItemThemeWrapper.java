package com.example.mat.roomdb_mvvm.mainactivity.model;

import com.example.mat.roomdb_mvvm.color.entity.Theme;

import java.util.List;

public class MenuItemThemeWrapper {

    private List<DrawerMenuItem> drawerMenuItem;
    private Theme theme;

    public MenuItemThemeWrapper() {

    }

    public List<DrawerMenuItem> getDrawerMenuItem() {
        return drawerMenuItem;
    }

    public void setDrawerMenuItem(List<DrawerMenuItem> drawerMenuItem) {
        this.drawerMenuItem = drawerMenuItem;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
