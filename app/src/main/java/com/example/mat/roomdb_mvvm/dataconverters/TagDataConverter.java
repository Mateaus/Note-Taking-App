package com.example.mat.roomdb_mvvm.dataconverters;

import androidx.room.TypeConverter;

import com.example.mat.roomdb_mvvm.mainactivity.entity.Tag;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TagDataConverter {

    @TypeConverter
    public String fromList(List<Tag> items) {
        if (items == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Tag>>() {
        }.getType();
        String json = gson.toJson(items, type);
        return json;
    }

    @TypeConverter
    public List<Tag> toList(String item) {
        if (item == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Tag>>() {
        }.getType();
        List<Tag> items = gson.fromJson(item, type);
        return items;
    }
}
