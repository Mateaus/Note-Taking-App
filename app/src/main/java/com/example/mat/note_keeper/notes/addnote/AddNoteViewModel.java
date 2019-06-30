package com.example.mat.note_keeper.notes.addnote;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.notes.note.NoteViewModel;
import com.example.mat.note_keeper.notes.note.entity.Note;

public class AddNoteViewModel extends NoteViewModel {

    private MutableLiveData<Integer> message;

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        this.message = new MutableLiveData<>();
    }

    public void addNote(Note note) {
        String title = note.getNoteTitle();
        String description = note.getNoteDescription();

        if (isEmpty(title) && isEmpty(description)) {
            message.setValue(R.string.note_cancelled);
        } else {
            insert(note);
            message.setValue(R.string.note_created);
        }
    }

    public LiveData<Integer> getMessage() {
        return this.message;
    }
}
