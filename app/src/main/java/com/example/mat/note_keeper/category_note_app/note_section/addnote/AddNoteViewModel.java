package com.example.mat.note_keeper.category_note_app.note_section.addnote;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.category_note_app.note_section.note.entity.Note;

import static android.app.Activity.RESULT_OK;

public class AddNoteViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> message;

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        this.message = new MutableLiveData<>();
    }

    public void addNote(Note note, Fragment fragment) {
        String title = note.getNtitle();
        String description = note.getNdescription();

        if(title.trim().isEmpty() && description.trim().isEmpty()) {
            fragment.getFragmentManager().popBackStack();
        } else {
            Bundle extras = new Bundle();
            extras.putString(AddNoteFragment.EXTRA_TITLE, title);
            extras.putString(AddNoteFragment.EXTRA_DESCRIPTION, description);
            fragment.getFragmentManager().popBackStackImmediate();
            fragment.getTargetFragment().onActivityResult(
                    fragment.getTargetRequestCode(),
                    RESULT_OK,
                    new Intent().putExtras(extras)
            );
            message.setValue(R.string.note_created);
        }

    }

    public LiveData<Integer> getMessage() {
        return this.message;
    }
}
