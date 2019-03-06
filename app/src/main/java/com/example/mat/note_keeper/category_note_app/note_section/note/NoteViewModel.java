package com.example.mat.note_keeper.category_note_app.note_section.note;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mat.note_keeper.color.entity.Color;
import com.example.mat.note_keeper.color.ColorRepository;
import com.example.mat.note_keeper.category_note_app.note_section.note.entity.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private ColorRepository colorRepository;
    private LiveData<List<Note>> allNotes;
    private LiveData<Color> selectedColor;

    public NoteViewModel(@NonNull Application application, int catalogueId) {
        super(application);
        this.noteRepository = new NoteRepository(application, catalogueId);
        this.colorRepository = new ColorRepository(application);
        this.allNotes = noteRepository.getAllNotes();
        this.selectedColor = colorRepository.getSelectedColors();
    }

    public void insert(Note note) {
        note.setNdate(getCurrentDate());
        noteRepository.insert(note);
    }

    public void update(Note note) {
        note.setNdate(getCurrentDate());
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

    public LiveData<Color> getSelectedColor() {
        return selectedColor;
    }

    public void updateColor(Color color) {
        colorRepository.update(color);
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
