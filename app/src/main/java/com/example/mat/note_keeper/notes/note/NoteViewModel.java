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

    private MainRepository mainRepository;
    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Note>> getNotes;

    public NoteViewModel(Application application) {
        super(application);
        this.mainRepository = new MainRepository(application);
        this.noteRepository = new NoteRepository(application);
        this.allNotes = noteRepository.getAllNotes();
    }

    public NoteViewModel(@NonNull Application application, String tag) {
        super(application);
        this.mainRepository = new MainRepository(application);
        this.noteRepository = new NoteRepository(application, tag);
        this.allNotes = noteRepository.getAllNotes();
        this.getNotes = noteRepository.getNotes();
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

    public void updateMenuItem(MenuItem menuItem) {
        mainRepository.updateMenuItem(menuItem);
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }

    public void deleteAllNotes() {
        noteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Note>> getNotes() {
        return getNotes;
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
