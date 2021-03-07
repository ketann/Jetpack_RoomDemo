package com.example.jetpack_roomdemo;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase noteDB;
    private LiveData<List<Note>> mAllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteDB = NoteRoomDatabase.getDatabase(application);
        noteDao = noteDB.noteDao();
        mAllNotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new InsertAsyncTask(noteDao).execute(note);
    }

    LiveData<List<Note>> getmAllNotes() {
        return mAllNotes;
    }

    public void update(Note note) {
        new UpdateAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteAsyncTask(noteDao).execute(note);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

        NoteDao mNoteDao;

        public InsertAsyncTask(NoteDao noteDao) {
            this.mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.insert(notes[0]);
            return null;
        }

    }

    private class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

        NoteDao mNoteDao;

        public UpdateAsyncTask(NoteDao noteDao) {
            this.mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.update(notes[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {
        NoteDao mNoteDao;

        public DeleteAsyncTask(NoteDao noteDao) {
            this.mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.delete(notes[0]);
            return null;
        }
    }
}
