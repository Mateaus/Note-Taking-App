package com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.mat.note_keeper.note_catalogue_app.catalogue_section.catalogue.entity.Catalogue;
import com.example.mat.note_keeper.database.CatalogueDao;
import com.example.mat.note_keeper.database.NoteDatabase;

import java.util.List;

public class CatalogueRepository {
    private CatalogueDao catalogueDao;
    private LiveData<List<Catalogue>> allCatalogues;

    public CatalogueRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        catalogueDao = database.catalogueDao();
        allCatalogues = catalogueDao.getAllCatalogues();
    }

    public void insert(Catalogue catalogue) {
        new InsertCatalogueAsyncTask(catalogueDao).execute(catalogue);
    }

    public void update(Catalogue catalogue) {
        new UpdateCatalogueAsyncTask(catalogueDao).execute(catalogue);
    }

    public void delete(Catalogue catalogue) {
        new DeleteCatalogueAsyncTask(catalogueDao).execute(catalogue);
    }

    public LiveData<List<Catalogue>> getAllCatalogues() {
        return allCatalogues;
    }

    private static class InsertCatalogueAsyncTask extends AsyncTask<Catalogue, Void, Void> {
        private CatalogueDao catalogueDao;

        private InsertCatalogueAsyncTask(CatalogueDao catalogueDao) {
            this.catalogueDao = catalogueDao;
        }

        @Override
        protected Void doInBackground(Catalogue... catalogues) {
            catalogueDao.insert(catalogues[0]);
            return null;
        }
    }

    private static class UpdateCatalogueAsyncTask extends AsyncTask<Catalogue, Void, Void> {
        private CatalogueDao catalogueDao;

        private UpdateCatalogueAsyncTask(CatalogueDao catalogueDao) {
            this.catalogueDao = catalogueDao;
        }

        @Override
        protected Void doInBackground(Catalogue... catalogues) {
            catalogueDao.update(catalogues[0]);
            return null;
        }
    }

    private static class DeleteCatalogueAsyncTask extends AsyncTask<Catalogue, Void, Void> {
        private CatalogueDao catalogueDao;

        private DeleteCatalogueAsyncTask(CatalogueDao catalogueDao) {
            this.catalogueDao = catalogueDao;
        }

        @Override
        protected Void doInBackground(Catalogue... catalogues) {
            catalogueDao.delete(catalogues[0]);
            return null;
        }
    }
}
