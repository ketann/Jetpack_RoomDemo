package com.example.jetpack_roomdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateNoteActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private EditText edtNote;
    public static final String NOTE_ADDED = "new_note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        edtNote = findViewById(R.id.edtNote);
        Button button = findViewById(R.id.btnCreateNote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                if (TextUtils.isEmpty(edtNote.getText())) {
                    setResult(RESULT_CANCELED, resultIntent);
                } else {
                    String note = edtNote.getText().toString();
                    resultIntent.putExtra(NOTE_ADDED, note);
                    setResult(RESULT_OK, resultIntent);
                    Log.i(TAG, "NoteSet");
                }
                finish();
            }
        });
    }
}