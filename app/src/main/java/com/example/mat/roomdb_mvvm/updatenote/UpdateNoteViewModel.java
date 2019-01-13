package com.example.mat.roomdb_mvvm.updatenote;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.note.entity.Note;

import static android.app.Activity.RESULT_OK;
import static com.example.mat.roomdb_mvvm.updatenote.UpdateNoteFragment.EXTRA_DESCRIPTION;
import static com.example.mat.roomdb_mvvm.updatenote.UpdateNoteFragment.EXTRA_ID;
import static com.example.mat.roomdb_mvvm.updatenote.UpdateNoteFragment.EXTRA_TITLE;

public class UpdateNoteViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> message;

    public UpdateNoteViewModel(@NonNull Application application) {
        super(application);
        this.message = new MutableLiveData<>();
    }


    public void updateNote(Note note, Fragment fragment) {

        String exId = fragment.getArguments().getString("id");
        String title = note.getTitle();
        String exTitle = fragment.getArguments().getString("title");
        String description = note.getDescription();
        String exDescription = fragment.getArguments().getString("description");

        if (title.equals(exTitle) && description.equals(exDescription)) {
            fragment.getFragmentManager().popBackStack();
        } else {
            Bundle extras = new Bundle();
            extras.putString(EXTRA_ID, exId);
            extras.putString(EXTRA_TITLE, title);
            extras.putString(EXTRA_DESCRIPTION, description);
            // popBackStackImmediate before data arrives to onActivityResult lifecycle
            fragment.getFragmentManager().popBackStackImmediate();
            fragment.getTargetFragment().onActivityResult(
                    fragment.getTargetRequestCode(),
                    RESULT_OK,
                    new Intent().putExtras(extras)
            );
            message.postValue(R.string.note_updated);
        }
    }

    public LiveData<Integer> getMessage() {
        return message;
    }
}
