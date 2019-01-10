package com.example.mat.roomdb_mvvm.addnote;

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
import com.example.mat.roomdb_mvvm.note.entity.Note;

import static android.app.Activity.RESULT_OK;

public class AddNoteViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> progress;
    private MutableLiveData<Integer> message;

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        this.progress = new MutableLiveData<>();
        this.message = new MutableLiveData<>();
    }

    public void addNote(Note note, DialogInterface dialog, Fragment fragment) {
        progress.setValue(true);
        String title = note.getTitle();
        String description = note.getDescription();

        if (title.trim().isEmpty() && description.trim().isEmpty()) {
            progress.setValue(false);
            dialog.dismiss();
        } else {
            Bundle extras = new Bundle();
            extras.putString(AddNoteFragment.EXTRA_TITLE, title);
            extras.putString(AddNoteFragment.EXTRA_DESCRIPTION, description);

            fragment.getTargetFragment().onActivityResult(
                    fragment.getTargetRequestCode(),
                    RESULT_OK,
                    new Intent().putExtras(extras)
            );
            message.setValue(R.string.note_created);
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
