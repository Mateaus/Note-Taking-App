package com.example.mat.roomdb_mvvm.color.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "theme_table")
public class Theme implements Parcelable {

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

    protected Theme(Parcel in) {
        id = in.readInt();
        themeStyle = in.readInt();
        primaryColor = in.readInt();
        primaryDarkColor = in.readInt();
        primaryLightColor = in.readInt();
        isDarkTheme = in.readByte() != 0;
    }

    public static final Creator<Theme> CREATOR = new Creator<Theme>() {
        @Override
        public Theme createFromParcel(Parcel in) {
            return new Theme(in);
        }

        @Override
        public Theme[] newArray(int size) {
            return new Theme[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(themeStyle);
        dest.writeInt(primaryColor);
        dest.writeInt(primaryDarkColor);
        dest.writeInt(primaryLightColor);
        dest.writeByte((byte) (isDarkTheme ? 1 : 0));
    }
}
