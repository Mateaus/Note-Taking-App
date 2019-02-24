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

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NoteViewModel(application, catalogueId);
    }
}
