package com.example.mat.roomdb_mvvm.notes.note;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mat.roomdb_mvvm.color.ColorViewModel;
import com.example.mat.roomdb_mvvm.notes.note.entity.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NoteViewModel extends ColorViewModel {

    private LiveData<List<Note>> allNotes;
    private LiveData<List<Note>> allNotesById;
    private NoteRepository noteRepository;

    public NoteViewModel(Application application) {
        super(application);
        this.noteRepository = new NoteRepository(application);
        this.allNotes = noteRepository.getAllNotes();
        this.allNotesById = noteRepository.getAllNotesById();
    }

    public void insert(Note note) {
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
        return allNotes;
    }

    public LiveData<List<Note>> getAllNotesById() {
        return allNotesById;
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
