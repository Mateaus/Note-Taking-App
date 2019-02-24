package com.example.mat.roomdb_mvvm.note_catalogue_app.catalogue.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "catalogue_table")
public class Catalogue {

    @ColumnInfo(name = "c_id")
    @PrimaryKey(autoGenerate = true)
    private int c_id;

    @ColumnInfo(name = "cname")
    private String cname;

    @ColumnInfo(name = "cdescription")
    private String cdescription;

    @Ignore
    public Catalogue(int c_id, String cname, String cdescription) {
        this.c_id = c_id;
        this.cname = cname;
        this.cdescription = cdescription;
    }

    public Catalogue(String cname, String cdescription) {
        this.cname = cname;
        this.cdescription = cdescription;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCdescription() {
        return cdescription;
    }

    public void setCdescription(String cdescription) {
        this.cdescription = cdescription;
    }
}
