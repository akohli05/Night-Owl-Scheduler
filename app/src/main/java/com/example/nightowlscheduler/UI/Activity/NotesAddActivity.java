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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nightowlscheduler.Database.SchedulerRepository;
import com.example.nightowlscheduler.Entity.Courses;
import com.example.nightowlscheduler.Entity.Notes;
import com.example.nightowlscheduler.Entity.Terms;
import com.example.nightowlscheduler.R;
import com.example.nightowlscheduler.UI.Adapters.CourseAdapter;

import java.util.List;

public class NotesAddActivity extends AppCompatActivity {
    //Repository
    SchedulerRepository repo;

    //Variables

    EditText addNoteTitle;
    EditText addNoteMessage;

    int courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);

        //Set title on toolbar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Add Note");

        //Note edit text views
        addNoteTitle = findViewById(R.id.addNoteTitle);
        addNoteMessage = findViewById(R.id.addNoteMessage);

        courseID = getIntent().getIntExtra("courseID", -1);
        repo = new SchedulerRepository(getApplication());
    }

    /**
     * Saves the new note
     * @param view
     */
    public void saveNewNote(View view) {
        //New note ID
        int newNoteID = repo.getAllNotes().size() + 1;

        //course to add
        Notes note = new Notes(newNoteID, addNoteTitle.getText().toString(),
                addNoteMessage.getText().toString(), courseID);

        boolean isValidNote = note.noteValidation();//validate the note

        if(isValidNote) {
            repo.insert(note);

            //Update confirmation
            Toast.makeText(getApplicationContext(),"Add Successful - Redirecting to Terms List",Toast.LENGTH_LONG).show();

            //Go back to Term list screen
            Intent intent = new Intent(NotesAddActivity.this, TermsListActivity.class );
            startActivity(intent);
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error adding note!")
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
     * Cancel new note
     * Go back to the Note List screen
     * @param view The view
     */
    public void cancelNewNote(View view) {
        //Update confirmation
        Toast.makeText(getApplicationContext(),"Cancel Successful - Redirecting to Notes List",Toast.LENGTH_LONG).show();

        Intent intent=new Intent(NotesAddActivity.this, NotesListActivity.class );
        intent.putExtra("courseID",courseID);
        startActivity(intent);
    }

}
