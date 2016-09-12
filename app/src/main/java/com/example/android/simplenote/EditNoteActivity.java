package com.example.android.simplenote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class EditNoteActivity extends AppCompatActivity {


    private EditText titleData;
    private EditText noteData;
    private int noteID;

    private DBAdapter mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        mDB = new DBAdapter(this);
        mDB.open();

        titleData = (EditText) findViewById(R.id.title_view);
        noteData = (EditText) findViewById(R.id.note_view);

        Intent editNote = getIntent();

        noteID = editNote.getIntExtra("id",0);
        String title = editNote.getStringExtra("Title");
        String note = editNote.getStringExtra("Note");

        titleData.setText(title);
        noteData.setText(note);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            boolean check = mDB.deleteRow((long) noteID);
            if (check)
                Toast.makeText(this, "Note Deleted!", Toast.LENGTH_SHORT).show();
            Intent goBack = new Intent(this, MainActivity.class);
            startActivity(goBack);
        }
        if (id == R.id.action_save) {
            if(TextUtils.isEmpty(titleData.getText()) || TextUtils.isEmpty(noteData.getText())){
                Toast.makeText(this, "Can't have empty fields!", Toast.LENGTH_SHORT).show();
            }
            if(!TextUtils.isEmpty(titleData.getText()) && !TextUtils.isEmpty(noteData.getText())) {
                boolean check = mDB.updateRow((long) noteID, titleData.getText().toString(), noteData.getText().toString());
                if(check)
                    Toast.makeText(this, "Note Updated!", Toast.LENGTH_SHORT).show();
                Intent goBack = new Intent(this, MainActivity.class);
                startActivity(goBack);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        mDB.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mDB.close();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mDB.close();
        super.onDestroy();
    }
}
