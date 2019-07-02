package com.example.mat.note_keeper.notes.note;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.example.mat.note_keeper.mainactivity.MainRepository;
import com.example.mat.note_keeper.mainactivity.model.MenuItem;
import com.example.mat.note_keeper.notes.note.entity.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;

    public NoteViewModel(Application application) {
        super(application);
        this.noteRepository = new NoteRepository(application);
    }

    public void insert(Note note) {
        if (isEmpty(note.getNoteTag())) {
            note.setNoteTag("default");
        }
        note.setNoteDate(getCurrentDate());
        noteRepository.insert(note);
    }

    public void update(Note note) {
        note.setNoteDate(getCurrentDate());
        noteRepository.update(note);
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }

    public void deleteAllNotes() {
        noteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return noteRepository.getAllNotes();
    }

    public LiveData<List<Note>> getAllFavoriteNotes(Boolean isFavorite) {
        return noteRepository.getAllFavoriteNotes(isFavorite);
    }

    public LiveData<List<Note>> getAllTagNotes(String tag) {
        return noteRepository.getAllTagNotes(tag);
    }

    public boolean isEmpty(String string) {
        return string.trim().length() == 0;
    }

    private String getCurrentDate() {
        DateFormat hourFormat = new SimpleDateFormat("h:mm a");
        DateFormat dateFormat = new SimpleDateFormat("M/d/y");
        String time = hourFormat.format(Calendar.getInstance().getTime());
        String date = dateFormat.format(Calendar.getInstance().getTime());
        String calendar = "\t" + time + "\n" + date;
        return calendar;
    }
}