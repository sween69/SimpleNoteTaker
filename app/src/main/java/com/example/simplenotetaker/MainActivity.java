package com.example.simplenotetaker;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ListView mListViewNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListViewNotes = findViewById(R.id.main_listview_notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_main_new_note:
                //start NoteActivity in NewNote mode
                Intent newNoteActivity = new Intent(this, NoteActivity.class);
                startActivity(newNoteActivity);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        //load saved notes into the listview
        //first, reset the listview
        mListViewNotes.setAdapter(null);
        ArrayList<Note> notes = Utilities.getAllSavedNotes(this);

        //sort notes from new to old
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                if(o1.getmDateTime() > o2.getmDateTime()){
                    return -1;
                }else{
                    return 1;
                }
            }
        });

        if(notes == null || notes.size() == 0){
            Toast.makeText(this, "You have no saved notes!", Toast.LENGTH_SHORT).show();
            return;
        }else{
            NoteAdapter na = new NoteAdapter(this, R.layout.item_note, notes);
            mListViewNotes.setAdapter(na);

            mListViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String fileName = ((Note)mListViewNotes.getItemAtPosition(position)).getmDateTime()
                            + Utilities.FILE_EXTENSION;

                    Intent viewNoteIntent = new Intent(getApplicationContext(), NoteActivity.class);
                    viewNoteIntent.putExtra("NOTE_FILE", fileName);
                    startActivity(viewNoteIntent);
                }
            });
        }
    }
}
