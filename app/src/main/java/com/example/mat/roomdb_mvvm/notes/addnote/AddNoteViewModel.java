package com.example.mat.roomdb_mvvm.notes.addnote;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mat.roomdb_mvvm.R;
import com.example.mat.roomdb_mvvm.mainactivity.MainRepository;
import com.example.mat.roomdb_mvvm.mainactivity.model.DrawerMenuItem;
import com.example.mat.roomdb_mvvm.notes.note.NoteViewModel;
import com.example.mat.roomdb_mvvm.notes.note.entity.Note;

import java.util.List;

public class AddNoteViewModel extends NoteViewModel {

    private MainRepository mainRepository;
    private MutableLiveData<Integer> message;

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        this.mainRepository = new MainRepository(application);
        this.message = new MutableLiveData<>();
    }

    public void addNote(Note note, DrawerMenuItem drawerMenuItem) {
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


    public LiveData<List<DrawerMenuItem>> getAllTagMenuItems() {
        return mainRepository.getAllTagMenuItems();
    }
}
