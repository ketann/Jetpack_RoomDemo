package com.example.jetpack_roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {
    private EditText edtNote;
    private EditNoteViewModel noteViewModel;
    private Bundle bundle;
    private String noteId;
    private LiveData<Note> note;
    public static final String NOTE_ID = "note_id";
    public static final String UPDATE_NOTE = "note_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        edtNote = findViewById(R.id.edtUpdateNote);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            noteId = bundle.getString("note_id");
        }
        noteViewModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);
        note = noteViewModel.getNote(noteId);
        note.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                edtNote.setText(note.getNote());
            }
        });
    }

    public void updateNote(View view) {
        String updateNote = edtNote.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(NOTE_ID, noteId);
        resultIntent.putExtra(UPDATE_NOTE, updateNote);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void updateCancel(View view) {
        finish();
    }
}