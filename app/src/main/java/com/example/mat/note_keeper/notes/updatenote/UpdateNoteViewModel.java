package com.example.mat.note_keeper.notes.updatenote;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.example.mat.note_keeper.R;
import com.example.mat.note_keeper.mainactivity.MainRepository;
import com.example.mat.note_keeper.mainactivity.entity.TagCategory;
import com.example.mat.note_keeper.notes.note.NoteViewModel;
import com.example.mat.note_keeper.notes.note.entity.Note;

import java.util.List;

public class UpdateNoteViewModel extends NoteViewModel {

    private MainRepository mainRepository;
    private MutableLiveData<Integer> message;

    public UpdateNoteViewModel(@NonNull Application application) {
        super(application);
        this.mainRepository = new MainRepository(application);
        this.message = new MutableLiveData<>();
    }


    public void updateNote(Note oldNote, Note newNote) {
        String oldTag = oldNote.getNoteTag();
        String newTag = newNote.getNoteTag();
        String oldTitle = oldNote.getNoteTitle();
        String newTitle = newNote.getNoteTitle();
        String oldDescription = oldNote.getNoteDescription();
        String newDescription = newNote.getNoteDescription();

        if (oldTag.equals(newTag) && oldTitle.equals(newTitle) && oldDescription.equals(newDescription)) {
            message.postValue(R.string.note_cancelled);
        } else {
            newNote.setNoteId(oldNote.getNoteId());
            update(newNote);
            message.postValue(R.string.note_updated);
        }
    }

    public LiveData<Integer> getMessage() {
        return message;
    }

    public LiveData<List<TagCategory>> getAllTagCategories() {
        return mainRepository.getAllTagCategories();
    }
}
