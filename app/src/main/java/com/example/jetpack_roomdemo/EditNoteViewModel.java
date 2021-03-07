package com.example.jetpack_roomdemo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EditNoteViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase database;

    public EditNoteViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "Edit ViewModel");
        database = NoteRoomDatabase.getDatabase(application);
        noteDao = database.noteDao();
    }
    public LiveData<Note> getNote(String noteId){
        return noteDao.getNote(noteId);
    }

}
