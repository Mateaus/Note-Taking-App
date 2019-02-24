package com.example.mat.note_keeper.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.entity.Catalogue;

import java.util.List;

@Dao
public interface CatalogueDao {

    @Insert
    void insert(Catalogue catalogue);

    @Update
    void update(Catalogue catalogue);

    @Delete
    void delete(Catalogue catalogue);

    @Query("SELECT * FROM catalogue_table")
    LiveData<List<Catalogue>> getAllCatalogues();


}
