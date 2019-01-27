package com.example.simplenotetaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(Context context, int resource, ArrayList<Note> notes) {
        super(context, resource, notes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_note, null);
        }

        Note note = getItem(position);

        if(note != null){
            TextView title = (TextView) convertView.findViewById(R.id.list_note_title);
            TextView date = convertView.findViewById(R.id.list_note_date);
            TextView content = convertView.findViewById(R.id.list_note_content);

            title.setText(note.getmTitle());
            date.setText(note.getDateTimeFormatted(getContext()));

            if(note.getmContent().length() > 50){
                content.setText(note.getmContent().substring(0, 50) + "...");
            }else{
                content.setText(note.getmContent());
            }
        }
            return convertView;
    }
}
