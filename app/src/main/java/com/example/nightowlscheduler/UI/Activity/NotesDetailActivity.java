package com.example.nightowlscheduler.UI.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.example.nightowlscheduler.Database.SchedulerRepository;
import com.example.nightowlscheduler.Entity.Assessments;
import com.example.nightowlscheduler.Entity.Notes;
import com.example.nightowlscheduler.R;

public class NotesDetailActivity extends AppCompatActivity {
    //Variables
    EditText editTitle;
    EditText editMessage;

    String title;
    String message;
    int courseID, noteID;

    SchedulerRepository schedulerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_detail);

        //Set title on toolbar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Note Details");

        //Edit assessment edit text views
        editTitle = findViewById(R.id.editNoteTitle);
        editMessage = findViewById(R.id.editNoteMessage);

        //Get the current assessment details
        noteID = getIntent().getIntExtra("noteID", -1);
        title = getIntent().getStringExtra("noteTitle");
        message = getIntent().getStringExtra("noteMessage");
        courseID = getIntent().getIntExtra("courseID", -1);

        //set the values
        editTitle.setText(title);
        editMessage.setText(message);

        schedulerRepository = new SchedulerRepository(getApplication());
    }

    /**
     * Share the note
     * @param view
     */
    public void shareNote(View view){
        Intent sendIntent=new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,editMessage.getText().toString());
        sendIntent.putExtra(Intent.EXTRA_TITLE,editTitle.getText().toString());
        sendIntent.setType("text/plain");
        Intent shareIntent=Intent.createChooser(sendIntent,null);
        startActivity(shareIntent);
    }
    /**
     * Updates the note
     * @param view
     */
    public void updateNote(View view) {
        Notes note = new Notes(noteID, editTitle.getText().toString(),
                editMessage.getText().toString(), courseID);

        boolean isValidNote = note.noteValidation();//validate the note

        if(isValidNote) {
            schedulerRepository.update(note);

            //Update confirmation
            Toast.makeText(getApplicationContext(),"Update Successful - Redirecting to Terms List",Toast.LENGTH_LONG).show();

            //Go back to the Terms Lists screen
            Intent intent=new Intent(NotesDetailActivity.this, TermsListActivity.class );
            startActivity(intent);
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error updating note!")
                    .setMessage("Please be aware of the following:\n" +
                            "\tAll fields are required.\n")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what should happen when negative button is clicked
                            Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();
        }
    }

    /**
     * Deletes the current note
     * @param view
     */
    public void deleteNote(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure to delete this note?")
                .setMessage("You will be redirected to the Terms List page on deletion.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Notes noteDel = new Notes(noteID, editTitle.getText().toString(),
                                editMessage.getText().toString(), courseID);

                        schedulerRepository.delete(noteDel);

                        //Go back to the Term Lists screen
                        Intent intent=new Intent(NotesDetailActivity.this, TermsListActivity.class );
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        Toast.makeText(getApplicationContext(),"Delete Cancelled",Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

}
