package com.example.mat.note_keeper.notes.note.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @ColumnInfo(name = "note_id")
    @PrimaryKey(autoGenerate = true)
    private int noteId;

    @ColumnInfo(name = "note_tag")
    private String noteTag;

    @ColumnInfo(name = "ntitle")
    private String noteTitle;

    @ColumnInfo(name = "ndescription")
    private String noteDescription;

    @ColumnInfo(name = "ndate")
    private String noteDate;

    @Ignore
    public Note(String noteTag, String noteTitle, String noteDescription) {
        this.noteTag = noteTag;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public Note(int noteId, String noteTag, String noteTitle, String noteDescription, String noteDate) {
        this.noteId = noteId;
        this.noteTag = noteTag;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteDate = noteDate;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTag() {
        return noteTag;
    }

    public void setNoteTag(String noteTag) {
        this.noteTag = noteTag;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }
}
