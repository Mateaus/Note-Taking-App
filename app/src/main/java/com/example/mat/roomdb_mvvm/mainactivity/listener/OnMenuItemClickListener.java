package com.example.mat.roomdb_mvvm.mainactivity.listener;

import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;

public interface OnMenuItemClickListener {
    void onMenuItemClick(DrawerMenuItem drawerMenuItem);
    void onMenuUpdateItemClick(DrawerMenuItem drawerMenuItem, int position);
    void onMenuDeleteClick(DrawerMenuItem drawerMenuItem, int position);
}
