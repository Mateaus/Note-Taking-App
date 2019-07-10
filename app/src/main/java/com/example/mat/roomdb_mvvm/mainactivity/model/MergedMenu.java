package com.example.mat.roomdb_mvvm.mainactivity.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergedMenu {

    private DrawerMenuItem menuOne;
    private DrawerMenuItem menuTwo;

    public MergedMenu() {

    }

    public DrawerMenuItem getMenuOne() {
        return menuOne;
    }

    public void setMenuOne(DrawerMenuItem menuOne) {
        this.menuOne = menuOne;
    }

    public DrawerMenuItem getMenuTwo() {
        return menuTwo;
    }

    public void setMenuTwo(DrawerMenuItem menuTwo) {
        this.menuTwo = menuTwo;
    }

    public List<DrawerMenuItem> getMenuList() {
        return new ArrayList<>(Arrays.asList(menuOne, menuTwo));
    }

    public boolean isComplete() {
        return (menuOne != null && menuTwo != null);
    }
}
