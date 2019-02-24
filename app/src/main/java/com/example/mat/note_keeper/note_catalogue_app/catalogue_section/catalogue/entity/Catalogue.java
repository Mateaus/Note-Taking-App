package com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "catalogue_table")
public class Catalogue {

    @ColumnInfo(name = "c_id")
    @PrimaryKey(autoGenerate = true)
    private int c_id;

    @ColumnInfo(name = "csubject")
    private String csubject;

    @ColumnInfo(name = "cdescription")
    private String cdescription;

    @Ignore
    public Catalogue(int c_id, String csubject, String cdescription) {
        this.c_id = c_id;
        this.csubject = csubject;
        this.cdescription = cdescription;
    }

    public Catalogue(String csubject, String cdescription) {
        this.csubject = csubject;
        this.cdescription = cdescription;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getCsubject() {
        return csubject;
    }

    public void getCsubject(String getCsubject) {
        this.csubject = getCsubject;
    }

    public String getCdescription() {
        return cdescription;
    }

    public void setCdescription(String cdescription) {
        this.cdescription = cdescription;
    }
}
