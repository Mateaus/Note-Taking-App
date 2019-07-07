package com.example.mat.roomdb_mvvm.notes.note.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerLayoutMenuItem;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "note_table", foreignKeys = {
        @ForeignKey(onDelete = CASCADE, entity = DrawerLayoutMenuItem.class,
                parentColumns = "menu_item_name", childColumns = "note_tag")},
        indices = {
                @Index("note_tag")
        })
public class Note {

    @ColumnInfo(name = "note_id")
    @PrimaryKey(autoGenerate = true)
    private int noteId;

    @ColumnInfo(name = "note_favorite")
    private boolean noteFavorite;

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

    @Ignore
    public Note(boolean noteFavorite, String noteTag, String noteTitle, String noteDescription) {
        this.noteFavorite = noteFavorite;
        this.noteTag = noteTag;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public Note(int noteId, boolean noteFavorite, String noteTag, String noteTitle, String noteDescription, String noteDate) {
        this.noteId = noteId;
        this.noteFavorite = noteFavorite;
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

    public boolean isNoteFavorite() {
        return noteFavorite;
    }

    public void setNoteFavorite(boolean noteFavorite) {
        this.noteFavorite = noteFavorite;
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
