package com.example.android.simplenote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hamza Basrai on 2016-04-16.
 */
public class DBAdapter {

    private static final String TAG = "DBAdapter"; //used for logging database version

    //Field Names
    public static final String KEY_ROWID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_NOTE = "note";

    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_TITLE, KEY_NOTE};

    //Column Numbers
    public static final int COL_ROWID = 0;
    public static final int COL_TITLE = 1;
    public static final int COL_NOTE = 2;

    //Database Info
    public static final String DATABASE_NAME = "dbNotes";
    public static final String DATABASE_TABLE = "notesMain";
    public static final int DATABASE_VERSION = 1;

    //SQL statement to create database
    private static final String DATABASE_CREATE_SQL =
            "CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TITLE + " TEXT NOT NULL, " + KEY_NOTE + " TEXT" + ");";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database version from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data");

            //Destroy old database
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            //Recreate new database
            onCreate(db);
        }
    }

    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    //Helper method to convert Cursor data into a Note object
    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.set_id(cursor.getInt(COL_ROWID));
        note.set_mTitle(cursor.getString(COL_TITLE));
        note.set_mNote(cursor.getString(COL_NOTE));
        return note;
    }

    public DBAdapter(Context context) {
        this.context = context;
        myDBHelper = new DatabaseHelper(context);
    }

    //Opens database connection
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    //Closes database connection
    public void close() {
        myDBHelper.close();
    }

    //Adda a new set of values to be inserted into the database
    public void insertRow(String title, String note) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_NOTE, note);

        //Insert the data into the database
        db.insert(DATABASE_TABLE, null, initialValues);
    }

    //Delete a row from the database by rowID (primary key)
    public boolean deleteRow(long rowID) {
        String where = KEY_ROWID + " = " + rowID;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    //Return all data in the database
    public List<Note> getAllRows() {
        List<Note> noteList = new ArrayList<>();

        Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS, null, null, null, null, null, null);
        if(c != null) {
            c.moveToFirst();
            while(!c.isAfterLast()){
                Note note = cursorToNote(c);
                noteList.add(note);
                c.moveToNext();
            }
        }
        c.close();
        return noteList;
    }

    //Get a specific row (by rowID)
    public Cursor getRow(long rowID) {
        String where = KEY_ROWID + " = " + rowID;
        Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }

    //Change an existing row to be equal to new data
    public boolean updateRow(long rowID, String title, String note){
        String where = KEY_ROWID + " = " + rowID;
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_TITLE, title);
        newValues.put(KEY_NOTE, note);

        //Insert the data into the database
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }
}
