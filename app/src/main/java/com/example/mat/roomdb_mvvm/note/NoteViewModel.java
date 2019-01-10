package com.example.mat.roomdb_mvvm.note;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mat.roomdb_mvvm.note.entity.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        this.repository = new NoteRepository(application);
        this.allNotes = repository.getAllNotes();
    }

    public void insert(Note note) {
        note.setDate(getCurrentDate());
        repository.insert(note);
    }

    public void update(Note note) {
        note.setDate(getCurrentDate());
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
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
