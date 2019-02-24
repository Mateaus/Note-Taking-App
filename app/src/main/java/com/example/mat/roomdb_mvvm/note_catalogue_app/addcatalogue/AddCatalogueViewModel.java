package com.example.mat.roomdb_mvvm.note_catalogue_app.addcatalogue;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.note_catalogue_app.catalogue.entity.Catalogue;

import static android.app.Activity.RESULT_OK;

public class AddCatalogueViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> progress;
    private MutableLiveData<Integer> message;

    public AddCatalogueViewModel(@NonNull Application application) {
        super(application);
        this.progress = new MutableLiveData<>();
        this.message = new MutableLiveData<>();
    }

    public void addCatalogue(Catalogue catalogue, DialogInterface dialog, Fragment fragment) {
        progress.setValue(true);
        String CatalogueName = catalogue.getCname();
        String CatalogueDescription = catalogue.getCdescription();

        if(CatalogueName.trim().isEmpty() && CatalogueDescription.trim().isEmpty()) {
            progress.setValue(false);
            dialog.dismiss();
        } else {
            Bundle extras = new Bundle();
            extras.putString(AddCatalogueDialogFragment.EXTRA_NAME, CatalogueName);
            extras.putString(AddCatalogueDialogFragment.EXTRA_DESCRIPTION, CatalogueDescription);

            fragment.getTargetFragment().onActivityResult(
                    fragment.getTargetRequestCode(),
                    RESULT_OK,
                    new Intent().putExtras(extras)
            );
            message.setValue(R.string.catalogue_created);
            progress.setValue(false);
            dialog.dismiss();
        }
    }

    public LiveData<Boolean> getProgress() {
        return this.progress;
    }

    public LiveData<Integer> getMessage() {
        return this.message;
    }
}
