package com.example.mat.roomdb_mvvm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;
import com.example.mat.roomdb_mvvm.notes.note.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM note_table WHERE note_favorite=:isFavorite ORDER BY note_id DESC")
    LiveData<List<Note>> getAllFavoriteNotes(Boolean isFavorite);

    @Query("SELECT * FROM note_table WHERE note_tag=:tag ORDER BY note_id DESC")
    LiveData<List<Note>> getAllNotesByTag(String tag);

    @Query("SELECT * FROM note_table ORDER BY note_id DESC")
    LiveData<List<Note>> getAllNotesByIds();

    @Query("SELECT *, (SELECT COUNT(*) FROM note_table) as menu_item_size FROM menu_item WHERE menu_item_id = 1")
    LiveData<DrawerMenuItem> getMenuOne();

    @Query("SELECT *, (SELECT COUNT(*) FROM note_table WHERE note_favorite = 1) as menu_item_size FROM menu_item WHERE menu_item_id = 2")
    LiveData<DrawerMenuItem> getMenuTwo();

    @Query("SELECT *, (SELECT COUNT(*) FROM note_table WHERE note_tag=menu_item.menu_item_name) as menu_item_size FROM menu_item WHERE menu_item_id > 2 ORDER BY menu_item_id DESC")
    LiveData<List<DrawerMenuItem>> getTagMenus();
}
