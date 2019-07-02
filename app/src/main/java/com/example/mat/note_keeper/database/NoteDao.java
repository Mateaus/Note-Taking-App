package com.example.mat.note_keeper.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mat.note_keeper.notes.note.entity.Note;

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

    @Query("SELECT * FROM note_table WHERE note_favorite=:isFavorite")
    LiveData<List<Note>> getAllFavoriteNotes(Boolean isFavorite);

    @Query("SELECT * FROM note_table WHERE note_tag=:tag")
    LiveData<List<Note>> getAllNotesByTag(String tag);

    @Query("SELECT * FROM note_table ORDER BY note_id DESC")
    LiveData<List<Note>> getAllNotesByIds();
}
