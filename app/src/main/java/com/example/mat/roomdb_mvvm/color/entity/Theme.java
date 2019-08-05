package com.example.mat.roomdb_mvvm.color.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "theme_table")
public class Theme {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "tstyle")
    private int themeStyle;

    @ColumnInfo(name = "tprimary")
    private int primaryColor;

    @ColumnInfo(name = "tprimary_dark")
    private int primaryDarkColor;

    @ColumnInfo(name = "tprimary_light")
    private int primaryLightColor;

    @ColumnInfo(name = "ttheme_type")
    private boolean isDarkTheme;

    @Ignore
    public Theme(int themeStyle, int primaryColor,
                 int primaryDarkColor, int primaryLightColor,
                 boolean isDarkTheme) {
        this.themeStyle = themeStyle;
        this.primaryColor = primaryColor;
        this.primaryDarkColor = primaryDarkColor;
        this.primaryLightColor = primaryLightColor;
        this.isDarkTheme = isDarkTheme;
    }

    public Theme(int id, int themeStyle, int primaryColor,
                 int primaryDarkColor, int primaryLightColor,
                 boolean isDarkTheme) {
        this.id = id;
        this.themeStyle = themeStyle;
        this.primaryColor = primaryColor;
        this.primaryDarkColor = primaryDarkColor;
        this.primaryLightColor = primaryLightColor;
        this.isDarkTheme = isDarkTheme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThemeStyle() {
        return themeStyle;
    }

    public void setThemeStyle(int themeStyle) {
        this.themeStyle = themeStyle;
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
    }

    public int getPrimaryDarkColor() {
        return primaryDarkColor;
    }

    public void setPrimaryDarkColor(int primaryDarkColor) {
        this.primaryDarkColor = primaryDarkColor;
    }

    public int getPrimaryLightColor() {
        return primaryLightColor;
    }

    public void setPrimaryLightColor(int primaryLightColor) {
        this.primaryLightColor = primaryLightColor;
    }

    public boolean isDarkTheme() {
        return isDarkTheme;
    }

    public void setDarkTheme(boolean darkTheme) {
        isDarkTheme = darkTheme;
    }
}
