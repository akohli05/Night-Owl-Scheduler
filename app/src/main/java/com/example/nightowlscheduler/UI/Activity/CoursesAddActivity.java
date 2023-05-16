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

public class CoursesAddActivity extends AppCompatActivity {
    //Repository
    SchedulerRepository repo;

    //Variables
    EditText addTitle;
    EditText addStartDate;
    EditText addEndDate;
    EditText addStatus;

    EditText addNoteTitle;
    EditText addNoteMessage;

    int courseTermID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course);

        //Set title on toolbar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Add Course");

        //Course edit text views
        addTitle = findViewById(R.id.addCourseTitle);
        addStartDate = findViewById(R.id.addCourseStartDate);
        addEndDate = findViewById(R.id.addCourseEndDate);
        addStatus = findViewById(R.id.addCourseStatus);

        //Note edit text views
        addNoteTitle = findViewById(R.id.addNoteTitle);
        addNoteMessage = findViewById(R.id.addNoteMessage);

        courseTermID = getIntent().getIntExtra("id", -1);
        repo = new SchedulerRepository(getApplication());
    }

    /**
     * Saves the new course
     * @param view
     */
    public void saveNewCourse(View view) {
        //New course ID
        int newID = repo.getAllCourses().size() + 1;

        //course to add
        Courses course = new Courses(newID, addTitle.getText().toString(),
                addStartDate.getText().toString(), addEndDate.getText().toString(), addStatus.getText().toString(), courseTermID);

        boolean isValid = course.courseValidation();//validate the course

        //New note ID
        int newNoteID = repo.getAllNotes().size() + 1;

        //note to add
        Notes note = new Notes(newNoteID, addNoteTitle.getText().toString(),
                addNoteMessage.getText().toString(), newID);

        boolean isValidNote = note.noteValidation();//validate the note

        if(isValid && isValidNote) {
            repo.insert(course);
            repo.insert(note);

            //Update confirmation
            Toast.makeText(getApplicationContext(),"Add Successful - Redirecting to Terms List",Toast.LENGTH_LONG).show();

            //Go back to Term list screen
            Intent intent = new Intent(CoursesAddActivity.this, TermsListActivity.class );
            startActivity(intent);
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error adding course and note!")
                    .setMessage("Please be aware of the following:\n" +
                            "\tAll Course and Note fields are required.\n" +
                            "\tThe Start Date cannot be equal to or after\n\tthe End Date.\n" +
                            "\tThe Start Date and End Date must be in\n\tthe specified format(MM/dd/yyyy).")
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
     * Cancel new course
     * Go back to the Term Details screen
     * @param view The view
     */
    public void cancelNewCourse(View view) {
        //Update confirmation
        Toast.makeText(getApplicationContext(),"Cancel Successful - Redirecting to Courses List",Toast.LENGTH_LONG).show();

        Intent intent=new Intent(CoursesAddActivity.this, CoursesListActivity.class );
        intent.putExtra("id",courseTermID);
        startActivity(intent);
    }

}
