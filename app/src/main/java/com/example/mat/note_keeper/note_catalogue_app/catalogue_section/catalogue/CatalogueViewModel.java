package com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.entity.Catalogue;

import java.util.List;

public class CatalogueViewModel  extends AndroidViewModel {

    private CatalogueRepository catalogueRepository;
    private LiveData<List<Catalogue>> allCatalogues;

    public CatalogueViewModel(@NonNull Application application) {
        super(application);
        this.catalogueRepository = new CatalogueRepository(application);
        this.allCatalogues = catalogueRepository.getAllCatalogues();
    }

    public void insert(Catalogue catalogue) {
        catalogueRepository.insert(catalogue);
    }

    public void update(Catalogue catalogue) {
        catalogueRepository.update(catalogue);
    }

    public void delete(Catalogue catalogue) {
        catalogueRepository.delete(catalogue);
    }

    public LiveData<List<Catalogue>> getAllCatalogues() {
        return allCatalogues;
    }
}
