package com.example.mat.note_keeper.notes.note;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mat.note_keeper.database.NoteDao;
import com.example.mat.note_keeper.database.NoteDatabase;
import com.example.mat.note_keeper.notes.note.entity.Note;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return noteDao.getAllNotes();
    }

    public LiveData<List<Note>> getAllFavoriteNotes(Boolean isFavorite) {
        return noteDao.getAllFavoriteNotes(isFavorite);
    }

    public LiveData<List<Note>> getAllTagNotes(String tag) {
        return noteDao.getAllNotesByTag(tag);
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        private DeleteAllNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
