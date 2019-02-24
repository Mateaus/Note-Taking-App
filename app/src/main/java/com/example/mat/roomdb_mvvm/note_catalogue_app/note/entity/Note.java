package com.example.mat.roomdb_mvvm.note_catalogue_app.note.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.example.mat.roomdb_mvvm.note_catalogue_app.catalogue.entity.Catalogue;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "note_table", foreignKeys = {
        @ForeignKey(onDelete = CASCADE, entity = Catalogue.class,
        parentColumns = "c_id", childColumns = "c_id")},
        indices = {
            @Index("c_id"),
        })
public class Note {

    @ColumnInfo(name = "n_id")
    @PrimaryKey(autoGenerate = true)
    private int n_id;

    @ColumnInfo(name = "c_id")
    private int c_id;

    @ColumnInfo(name = "ntitle")
    private String ntitle;

    @ColumnInfo(name = "ndescription")
    private String ndescription;

    @ColumnInfo(name = "ndate")
    private String ndate;

    @Ignore
    public Note(String ntitle, String ndescription) {
        this.ntitle = ntitle;
        this.ndescription = ndescription;
    }

    public Note(int n_id, int c_id, String ntitle, String ndescription) {
        this.n_id = n_id;
        this.c_id = c_id;
        this.ntitle = ntitle;
        this.ndescription = ndescription;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getN_id() {
        return n_id;
    }

    public void setN_id(int n_id) {
        this.n_id = n_id;
    }

    public String getNtitle() {
        return ntitle;
    }

    public void setNtitle(String ntitle) {
        this.ntitle = ntitle;
    }

    public String getNdescription() {
        return ndescription;
    }

    public void setNdescription(String ndescription) {
        this.ndescription = ndescription;
    }

    public String getNdate() {
        return ndate;
    }

    public void setNdate(String ndate) {
        this.ndate = ndate;
    }
}
