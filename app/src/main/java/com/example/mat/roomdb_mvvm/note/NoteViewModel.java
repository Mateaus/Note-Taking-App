package com.example.mat.roomdb_mvvm.note;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mat.roomdb_mvvm.color.entity.Color;
import com.example.mat.roomdb_mvvm.note.entity.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private ColorRepository colorRepository;
    private LiveData<List<Note>> allNotes;
    private LiveData<Color> selectedColor;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        this.noteRepository = new NoteRepository(application);
        this.colorRepository = new ColorRepository(application);
        this.allNotes = noteRepository.getAllNotes();
        this.selectedColor = colorRepository.getSelectedColors();
    }

    public void insert(Note note) {
        note.setDate(getCurrentDate());
        noteRepository.insert(note);
    }

    public void update(Note note) {
        note.setDate(getCurrentDate());
        noteRepository.update(note);
    }

    public void updateColor(Color color) {
        colorRepository.update(color);
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

    public LiveData<Color> getSelectedColor() {
        return selectedColor;
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
