package com.example.mat.note_keeper.category_note_app.note_section.updatenote;

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
import static com.example.mat.note_keeper.category_note_app.note_section.updatenote.UpdateNoteFragment.EXTRA_CID;
import static com.example.mat.note_keeper.category_note_app.note_section.updatenote.UpdateNoteFragment.EXTRA_DESCRIPTION;
import static com.example.mat.note_keeper.category_note_app.note_section.updatenote.UpdateNoteFragment.EXTRA_NID;
import static com.example.mat.note_keeper.category_note_app.note_section.updatenote.UpdateNoteFragment.EXTRA_TITLE;

public class UpdateNoteViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> message;

    public UpdateNoteViewModel(@NonNull Application application) {
        super(application);
        this.message = new MutableLiveData<>();
    }


    public void updateNote(Note note, Fragment fragment) {
        String cid = fragment.getArguments().getString("cid");
        String nid = fragment.getArguments().getString("nid");
        String title = note.getNtitle();
        String exTitle = fragment.getArguments().getString("title");
        String description = note.getNdescription();
        String exDescription = fragment.getArguments().getString("description");

        if (title.equals(exTitle) && description.equals(exDescription)) {
            fragment.getFragmentManager().popBackStack();
        } else {
            Bundle extras = new Bundle();
            extras.putString(EXTRA_CID, cid);
            extras.putString(EXTRA_NID, nid);
            extras.putString(EXTRA_TITLE, title);
            extras.putString(EXTRA_DESCRIPTION, description);
            // popBackStackImmediate before data arrives to onActivityResult lifecycle
            fragment.getFragmentManager().popBackStackImmediate();
            fragment.getTargetFragment().onActivityResult(
                    fragment.getTargetRequestCode(),
                    RESULT_OK,
                    new Intent().putExtras(extras)
            );
            message.postValue(R.string.category_updated);
        }
    }

    public LiveData<Integer> getMessage() {
        return message;
    }
}
