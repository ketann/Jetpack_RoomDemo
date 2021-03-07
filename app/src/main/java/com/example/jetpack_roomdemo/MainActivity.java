package com.example.jetpack_roomdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NoteListAdapter.OnDeleteClickListener {

    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    private String TAG = this.getClass().getSimpleName();
    private NoteViewModel noteViewModel;
    private NoteListAdapter noteListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        RecyclerView recyclerView = findViewById(R.id.noteRecycleView);
        noteListAdapter = new NoteListAdapter(this, this);
        recyclerView.setAdapter(noteListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getmAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteListAdapter.setNotes(notes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // insert data into database
            final String note_id = UUID.randomUUID().toString();
            Note note = new Note(note_id, data.getStringExtra(CreateNoteActivity.NOTE_ADDED));
            noteViewModel.insert(note);
            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // code for update the note
            Note note = new Note(
                    data.getStringExtra(EditNoteActivity.NOTE_ID),
                    data.getStringExtra(EditNoteActivity.UPDATE_NOTE));
            noteViewModel.update(note);
            Toast.makeText(this, R.string.updated, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnDeleteClickListener(Note myNote) {
        //  Code for Delete operation
        deleteConfirmation(myNote);
    }

    public void deleteConfirmation(Note myNote) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        noteViewModel.delete(myNote);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}