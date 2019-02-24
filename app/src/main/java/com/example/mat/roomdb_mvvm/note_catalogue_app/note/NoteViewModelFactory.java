package com.example.mat.roomdb_mvvm.note_catalogue_app.note;


import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class NoteViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private int catalogueId;

    public NoteViewModelFactory(@NonNull Application application, int catalogueId) {
        this.application = application;
        this.catalogueId = catalogueId;
    }

    /**
     * create allows us to return X extra parameters to our NoteViewModel
     * to give us the ability to query the needed information from the DB.
     * Since this is returning a Generic type. The compiler will warns us
     * that this isn't safe because the generic can be converted to any type
     * of object but in our case, this should always remain a NoteViewModel
     * Object.
     * @param modelClass
     * @param <T>
     * @return new NoteViewModel extending the ViewModel
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NoteViewModel(application, catalogueId);
    }
}
