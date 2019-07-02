package com.example.mat.note_keeper.notes.note;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NoteViewFactoryModel implements ViewModelProvider.Factory {

    private Application application;
    private String tag;

    public NoteViewFactoryModel(Application application, String tag) {
        this.application = application;
        this.tag = tag;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NoteViewModel(application, tag);
    }
}
