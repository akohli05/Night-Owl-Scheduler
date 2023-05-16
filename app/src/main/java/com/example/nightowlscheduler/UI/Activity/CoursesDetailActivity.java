package com.example.nightowlscheduler.UI.Activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.example.nightowlscheduler.Database.SchedulerRepository;
import com.example.nightowlscheduler.Entity.Courses;
import com.example.nightowlscheduler.Entity.Mentors;
import com.example.nightowlscheduler.R;
import com.example.nightowlscheduler.UI.MyReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CoursesDetailActivity extends AppCompatActivity {
    //Variables for edit text views
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    EditText editStatus;

    String title;
    String startDate;
    String endDate;
    String status;
    int termID;

    int courseID;
    SchedulerRepository schedulerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);

        //Set title on toolbar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Course Details");

        //Edit course edit text views
        editTitle = findViewById(R.id.editCourseTitle);
        editStartDate = findViewById(R.id.editCourseStartDate);
        editEndDate = findViewById(R.id.editCourseEndDate);
        editStatus = findViewById(R.id.editCourseStatus);

        //Get the current course details
        courseID = getIntent().getIntExtra("courseID", -1);
        title = getIntent().getStringExtra("courseTitle");
        startDate = getIntent().getStringExtra("courseStartDate");
        endDate = getIntent().getStringExtra("courseEndDate");
        status = getIntent().getStringExtra("courseStatus");
        termID = getIntent().getIntExtra("courseTermID", -1);

        //set the values
        editTitle.setText(title);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);
        editStatus.setText(status);

        schedulerRepository = new SchedulerRepository(getApplication());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notify_course_menu, menu);
        return true;
    }

    /**
     * notification for the start and end dates
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notifyStartCourse:
                String startDateFromScreen = editStartDate.getText().toString();
                String myFormatSD = "MM/dd/yyyy";
                SimpleDateFormat sdfStart = new SimpleDateFormat(myFormatSD, Locale.US);
                Date myStartDate = null;
                try {
                    myStartDate = sdfStart.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long triggerSD = myStartDate.getTime();
                Intent intentSD= new Intent(CoursesDetailActivity.this, MyReceiver.class);
                intentSD.putExtra("key","Course starting today!");
                PendingIntent senderSD = PendingIntent.getBroadcast(CoursesDetailActivity.this, ++MainActivity.numAlert,intentSD, PendingIntent.FLAG_MUTABLE);
                AlarmManager alarmManagerSD = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManagerSD.set(AlarmManager.RTC_WAKEUP, triggerSD, senderSD);
                return true;

            case R.id.notifyEndCourse:
                String endDateFromScreen = editEndDate.getText().toString();
                Date myEndDate = null;
                String myFormatED = "MM/dd/yyyy";
                SimpleDateFormat sdfEnd = new SimpleDateFormat(myFormatED, Locale.US);
                try {
                    myEndDate = sdfEnd.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long triggerED = myEndDate.getTime();
                Intent intentED = new Intent(CoursesDetailActivity.this, MyReceiver.class);
                intentED.putExtra("key","Course ending today!");
                PendingIntent senderED = PendingIntent.getBroadcast(CoursesDetailActivity.this, ++MainActivity.numAlert,intentED, PendingIntent.FLAG_MUTABLE);
                AlarmManager alarmManagerED = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManagerED.set(AlarmManager.RTC_WAKEUP, triggerED, senderED);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Updates the current term
     * @param view
     */
    public void updateCourse(View view) {
        Courses course = new Courses(courseID, editTitle.getText().toString(),
                editStartDate.getText().toString(), editEndDate.getText().toString(),
                editStatus.getText().toString(), termID);

        boolean isValid = course.courseValidation();//validate the course

        if(isValid) {
            schedulerRepository.update(course);

            //Update confirmation
            Toast.makeText(getApplicationContext(),"Update Successful - Redirecting to Terms List",Toast.LENGTH_LONG).show();

            //Go back to the Terms Lists screen
            Intent intent=new Intent(CoursesDetailActivity.this, TermsListActivity.class );
            startActivity(intent);
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error updating course!")
                    .setMessage("Please be aware of the following:\n" +
                            "\tAll fields are required.\n" +
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
     * Deletes the current course
     * @param view
     */
    public void deleteCourse(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
        .setIcon(android.R.drawable.ic_dialog_alert)
         .setTitle("Are you sure to delete this course?")
            .setMessage("You will be redirected to the Terms List page on deletion.")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Courses course = new Courses(courseID, editTitle.getText().toString(),
                                editStartDate.getText().toString(), editEndDate.getText().toString(),
                                editStatus.getText().toString(), termID);

                        schedulerRepository.delete(course);

                        //Go back to the Term Lists screen
                        Intent intent=new Intent(CoursesDetailActivity.this, TermsListActivity.class );
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

    /**
     * Go to the Assessments List screen
     * @param view The view
     */
    public void goToAssessmentsListScreen(View view) {
        Intent intent=new Intent(CoursesDetailActivity.this, AssessmentsListActivity.class );
        intent.putExtra("courseID",courseID);
        startActivity(intent);
    }

    /**
     * Go to the Mentors List screen
     * @param view The view
     */
    public void goToMentorsListScreen(View view) {
        Intent intent=new Intent(CoursesDetailActivity.this, MentorsListActivity.class );

        //Add sample mentor
        Mentors mentor = new Mentors(1, "John Doe", "123-123-1234", "test@cloud.com", courseID);
        schedulerRepository.insert(mentor);
        intent.putExtra("courseID",courseID);

        startActivity(intent);
    }

    /**
     * Go to the Notes List screen
     * @param view The view
     */
    public void goToNotesListScreen(View view) {
        Intent intent=new Intent(CoursesDetailActivity.this, NotesListActivity.class );
        intent.putExtra("courseID",courseID);
        startActivity(intent);
    }
}
