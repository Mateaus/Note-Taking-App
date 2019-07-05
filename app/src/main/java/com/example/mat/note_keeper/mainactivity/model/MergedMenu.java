package com.example.mat.note_keeper.mainactivity.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergedMenu {

    private DrawerLayoutMenuItem menuOne;
    private DrawerLayoutMenuItem menuTwo;

    public MergedMenu() {

    }

    public DrawerLayoutMenuItem getMenuOne() {
        return menuOne;
    }

    public void setMenuOne(DrawerLayoutMenuItem menuOne) {
        this.menuOne = menuOne;
    }

    public DrawerLayoutMenuItem getMenuTwo() {
        return menuTwo;
    }

    public void setMenuTwo(DrawerLayoutMenuItem menuTwo) {
        this.menuTwo = menuTwo;
    }

    public List<DrawerLayoutMenuItem> getMenuList() {
        return new ArrayList<>(Arrays.asList(menuOne, menuTwo));
    }

    public boolean isComplete() {
        return (menuOne != null && menuTwo != null);
    }
}
