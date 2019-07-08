package com.example.mat.roomdb_mvvm.mainactivity.listener;

import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerLayoutMenuItem;

public interface OnMenuItemClickListener {
    void onMenuItemClick(DrawerLayoutMenuItem drawerLayoutMenuItem);
    void onMenuUpdateItemClick(DrawerLayoutMenuItem drawerLayoutMenuItem, int position);
    void onMenuDeleteClick(DrawerLayoutMenuItem drawerLayoutMenuItem, int position);
}
