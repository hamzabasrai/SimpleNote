package com.example.android.simplenote;

/**
 * Created by Hamza Basrai on 2016-04-14.
 */
public class Note {

    private int _id;
    private String _mTitle;
    private String _mNote;

    public Note(int id, String title, String note){
        this._id = id;
        this._mTitle = title;
        this._mNote = note;
    }

    public Note(){

    }

    public int get_id() {
        return _id;
    }

    public String get_mTitle() {
        return _mTitle;
    }

    public String get_mNote(){
        return _mNote;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_mTitle(String _mTitle) {
        this._mTitle = _mTitle;
    }

    public void set_mNote(String _mNote) {
        this._mNote = _mNote;
    }
}
