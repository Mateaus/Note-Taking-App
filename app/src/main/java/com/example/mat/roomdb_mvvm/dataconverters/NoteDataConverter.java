package com.example.mat.roomdb_mvvm.dataconverters;

import androidx.room.TypeConverter;

import com.example.mat.roomdb_mvvm.notes.note.entity.Note;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class NoteDataConverter {

    @TypeConverter
    public String fromList(List<Note> items) {
        if (items == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Note>>() {
        }.getType();
        String json = gson.toJson(items, type);
        return json;
    }

    @TypeConverter
    public List<Note> toList(String item) {
        if (item == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Note>>() {
        }.getType();
        List<Note> items = gson.fromJson(item, type);
        return items;
    }
}
