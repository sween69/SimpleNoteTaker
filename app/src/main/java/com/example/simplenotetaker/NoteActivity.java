package com.example.simplenotetaker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private EditText mEtTitle;
    private EditText mEtContent;

    private String mNoteFileName;
    private Note mLoadedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mEtTitle = (EditText)  findViewById(R.id.note_et_title);
        mEtContent = (EditText)  findViewById(R.id.note_et_content);

        //check if view/edit note bundle is set, otherwise user wants to create new note
        mNoteFileName = getIntent().getStringExtra("NOTE_FILE");
        if(mNoteFileName != null && !mNoteFileName.isEmpty()){
            mLoadedNote = Utilities.getNoteByName(this, mNoteFileName);

            if(mLoadedNote != null){
                mEtTitle.setText(mLoadedNote.getmTitle());
                mEtContent.setText(mLoadedNote.getmContent());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_note_save:
                saveNote();
                break;

            case R.id.action_note_delete:
                deleteNote();
                break;
        }
        return true;
    }

    private void saveNote(){
        Note note;
        
        //trim() is used for check the whitesapce 
        //if(mEtTitle.getText().toString().trim().isEmpty() || mEtContent.getText().toString().trim().isEmpty()){
        if(mEtTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Please enter a title and a content", Toast.LENGTH_SHORT).show();
            return; //stay in current activity
        }

        if(mLoadedNote == null){//create a new note
            note = new Note(System.currentTimeMillis(), mEtTitle.getText().toString()
                    ,mEtContent.getText().toString() );
        }else{//modify the existing note
            Utilities.deleteNote(getApplicationContext()
                    , mLoadedNote.getmDateTime() + Utilities.FILE_EXTENSION);
            note = new Note(System.currentTimeMillis(), mEtTitle.getText().toString()
                    ,mEtContent.getText().toString() );
        }


        if(Utilities.saveNote(this, note)){
            Toast.makeText(this, "Your note is saved!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Cannot save the note, pls make sure you have enough space on the device"
                    , Toast.LENGTH_SHORT).show();
        }

        finish(); //exit the current activity and go back to the main activity
    }

    private void deleteNote(){
        if(mLoadedNote == null){ // delete a unsaved new note
            finish(); //closed and return to MainActivity
        }else { //delete the existing note
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setMessage("You are about to delete " + mEtTitle.getText().toString() + ", are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utilities.deleteNote(getApplicationContext()
                                    , mLoadedNote.getmDateTime() + Utilities.FILE_EXTENSION);
                            Toast.makeText(NoteActivity.this, mEtTitle.getText().toString() + " is deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .setCancelable(false);

            dialog.show();
        }
    }

}
