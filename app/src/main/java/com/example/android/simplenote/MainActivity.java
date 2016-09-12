package com.example.android.simplenote;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ListView mListView;
    public NoteAdapter mNoteAdapter;
    public DBAdapter mDB;
    public List<Note> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNote = new Intent(view.getContext(), AddNoteActivity.class);
                startActivity(addNote);

            }
        });

        mDB = new DBAdapter(this);
        mDB.open();
        values = mDB.getAllRows();

        mListView = (ListView) findViewById(R.id.note_listview);
        mNoteAdapter = new NoteAdapter(this, R.layout.note_item_layout, values);
        mListView.setAdapter(mNoteAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editNote = new Intent(view.getContext(), EditNoteActivity.class);
                editNote.putExtra("id", values.get(position).get_id());
                editNote.putExtra("Title", values.get(position).get_mTitle());
                editNote.putExtra("Note", values.get(position).get_mNote());
                startActivity(editNote);
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
