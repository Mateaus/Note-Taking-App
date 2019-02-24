package com.example.mat.roomdb_mvvm.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.mat.roomdb_mvvm.note_catalogue_app.note.entity.Note;

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

    @Query("SELECT * FROM note_table JOIN catalogue_table ON note_table.c_id=catalogue_table.c_id WHERE note_table.c_id=:catalogueId")
    LiveData<List<Note>> getAllNotes(int catalogueId);

    @Query("SELECT * FROM note_table ORDER BY n_id DESC")
    LiveData<List<Note>> getAllNotesByIds();
}
