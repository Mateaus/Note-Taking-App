package com.example.mat.note_keeper.color.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "color_table")
public class Color {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "cname")
    private String colorName;

    @ColumnInfo(name = "cstyle")
    private int colorStyle;

    @ColumnInfo(name = "cprimary")
    private int primaryColor;

    @ColumnInfo(name = "cprimary_dark")
    private int primaryDarkColor;

    @ColumnInfo(name = "cprimary_light")
    private int primaryLightColor;

    @Ignore
    public Color(String colorName, int colorStyle, int primaryColor,
                 int primaryDarkColor, int primaryLightColor) {
        this.colorName = colorName;
        this.colorStyle = colorStyle;
        this.primaryColor = primaryColor;
        this.primaryDarkColor = primaryDarkColor;
        this.primaryLightColor = primaryLightColor;
    }


    public Color(int id, String colorName, int colorStyle, int primaryColor,
                 int primaryDarkColor, int primaryLightColor) {
        this.id = id;
        this.colorName = colorName;
        this.colorStyle = colorStyle;
        this.primaryColor = primaryColor;
        this.primaryDarkColor = primaryDarkColor;
        this.primaryLightColor = primaryLightColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getColorStyle() {
        return colorStyle;
    }

    public void setColorStyle(int colorStyle) {
        this.colorStyle = colorStyle;
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
}
