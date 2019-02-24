package com.example.mat.roomdb_mvvm.note_catalogue_app.updatecatalogue;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.note_catalogue_app.catalogue.entity.Catalogue;

import static android.app.Activity.RESULT_OK;
import static com.example.mat.roomdb_mvvm.note_catalogue_app.updatecatalogue.UpdateCatalogueFragment.EXTRA_CDESCRIPTION;
import static com.example.mat.roomdb_mvvm.note_catalogue_app.updatecatalogue.UpdateCatalogueFragment.EXTRA_CID;
import static com.example.mat.roomdb_mvvm.note_catalogue_app.updatecatalogue.UpdateCatalogueFragment.EXTRA_CSUBJECT;

public class UpdateCatalogueViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> message;

    public UpdateCatalogueViewModel(@NonNull Application application) {
        super(application);
        this.message = new MutableLiveData<>();
    }

    public void updateCatalogue(Catalogue catalogue, Fragment fragment) {
        String cid = fragment.getArguments().getString("cid");
        String subject = catalogue.getCsubject().toString();
        String exsubject = fragment.getArguments().getString("csubject");
        String description = catalogue.getCdescription().toString();
        String exdescription = fragment.getArguments().getString("cdescription");

        if (subject.equals(exsubject) && description.equals(exdescription)) {
            fragment.getFragmentManager().popBackStack();
        } else {
            Bundle extras = new Bundle();
            extras.putString(EXTRA_CID, cid);
            extras.putString(EXTRA_CSUBJECT, subject);
            extras.putString(EXTRA_CDESCRIPTION, description);

            fragment.getFragmentManager().popBackStackImmediate();
            fragment.getTargetFragment().onActivityResult(
                    fragment.getTargetRequestCode(),
                    RESULT_OK,
                    new Intent().putExtras(extras)
            );
            message.postValue(R.string.catalogue_update);
        }
    }

    public LiveData<Integer> getMessage() {
        return message;
    }
}
