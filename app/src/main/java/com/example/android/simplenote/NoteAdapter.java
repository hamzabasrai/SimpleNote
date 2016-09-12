package com.example.android.simplenote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Hamza Basrai on 2016-04-14.
 */
public class NoteAdapter extends ArrayAdapter<Note> {

    private static class PlaceHolder {
        TextView titleData;
        TextView noteData;
        TextView dateStamp;
    }

    Context mContext;
    int mLayoutResourceId;
    List<Note> mData = null;
    Calendar cal = Calendar.getInstance();

    public NoteAdapter(Context context, int resource, List<Note> data) {
        super(context, resource, data);

        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mData = data;
    }

    @Override
    public Note getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        PlaceHolder holder = null;

        //If we do not currently have a row view to reuse
        if (row == null) {
            //Create new view
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new PlaceHolder();

            holder.titleData = (TextView) row.findViewById(R.id.title_textview);
            holder.noteData = (TextView) row.findViewById(R.id.note_textview);
            holder.dateStamp = (TextView) row.findViewById(R.id.dateStamp);

            row.setTag(holder);
        } else {
            //Otherwise use an existing view
            holder = (PlaceHolder) row.getTag();
        }

        //Get data from data array
        Note note = mData.get(position);

        //Change the views to match data
        holder.titleData.setText(note.get_mTitle());
        holder.noteData.setText(note.get_mNote());
        //holder.dateStamp.setText();
        return row;
    }

    @Override
    public void add(Note object) {
        super.add(object);
    }
}
