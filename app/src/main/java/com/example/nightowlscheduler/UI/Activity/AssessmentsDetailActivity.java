package com.example.nightowlscheduler.UI.Activity;

import android.app.AlarmManager;
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
import com.example.nightowlscheduler.DAO.AssessmentsDAO;
import com.example.nightowlscheduler.Database.SchedulerRepository;
import com.example.nightowlscheduler.Entity.Assessments;
import com.example.nightowlscheduler.R;
import com.example.nightowlscheduler.UI.MyReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AssessmentsDetailActivity extends AppCompatActivity {
    //Variables
    EditText editTitle;
    EditText editGoalStartDate;
    EditText editGoalEndDate;
    EditText editType;

    int assessmentID;
    String title;
    String goalStartDate;
    String goalEndDate;
    String type;
    int courseID;

    SchedulerRepository schedulerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_detail);

        //Set title on toolbar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Assessment Details");

        //Edit assessment edit text views
        editTitle = findViewById(R.id.editAssessmentTitle);
        editGoalStartDate = findViewById(R.id.editAssessmentGoalStart);
        editGoalEndDate = findViewById(R.id.editAssessmentGoalEnd);
        editType = findViewById(R.id.editAssessmentType);

        //Get the current assessment details
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        title = getIntent().getStringExtra("assessmentTitle");
        goalStartDate = getIntent().getStringExtra("assessmentGoalStartDate");
        goalEndDate = getIntent().getStringExtra("assessmentGoalEndDate");
        type = getIntent().getStringExtra("assessmentType");
        courseID = getIntent().getIntExtra("courseID", -1);

        //set the values
        editTitle.setText(title);
        editGoalStartDate.setText(goalStartDate);
        editGoalEndDate.setText(goalEndDate);
        editType.setText(type);

        schedulerRepository = new SchedulerRepository(getApplication());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notify_assessment_menu, menu);
        return true;
    }

    /**
     * notification for the start and end dates
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notifyStartAssessment:
                String startDateFromScreen = editGoalStartDate.getText().toString();
                String myFormatSD = "MM/dd/yyyy";
                SimpleDateFormat sdfStart = new SimpleDateFormat(myFormatSD, Locale.US);
                Date myStartDate = null;
                try {
                    myStartDate = sdfStart.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long triggerSD = myStartDate.getTime();
                Intent intentSD= new Intent(AssessmentsDetailActivity.this, MyReceiver.class);
                intentSD.putExtra("key","Assessment starting today!");
                PendingIntent senderSD = PendingIntent.getBroadcast(AssessmentsDetailActivity.this, ++MainActivity.numAlert,intentSD, PendingIntent.FLAG_MUTABLE);
                AlarmManager alarmManagerSD = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManagerSD.set(AlarmManager.RTC_WAKEUP, triggerSD, senderSD);
                return true;

            case R.id.notifyEndAssessment:
                String endDateFromScreen = editGoalEndDate.getText().toString();
                Date myEndDate = null;
                String myFormatED = "MM/dd/yyyy";
                SimpleDateFormat sdfEnd = new SimpleDateFormat(myFormatED, Locale.US);
                try {
                    myEndDate = sdfEnd.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long triggerED = myEndDate.getTime();
                Intent intentED = new Intent(AssessmentsDetailActivity.this, MyReceiver.class);
                intentED.putExtra("key","Assessment ending today!");
                PendingIntent senderED = PendingIntent.getBroadcast(AssessmentsDetailActivity.this, ++MainActivity.numAlert,intentED, PendingIntent.FLAG_MUTABLE);
                AlarmManager alarmManagerED = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManagerED.set(AlarmManager.RTC_WAKEUP, triggerED, senderED);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Updates the assessment
     * @param view
     */
    public void updateAssessment(View view) {
        Assessments assessment = new Assessments(assessmentID, editTitle.getText().toString(),
                editGoalStartDate.getText().toString(), editGoalEndDate.getText().toString(),
                editType.getText().toString(), courseID);

        boolean isValid = assessment.assessmentValidation();//validate the assessment

        if(isValid) {
            schedulerRepository.update(assessment);

            //Update confirmation
            Toast.makeText(getApplicationContext(),"Update Successful - Redirecting to Terms List",Toast.LENGTH_LONG).show();

            //Go back to the Terms Lists screen
            Intent intent=new Intent(AssessmentsDetailActivity.this, TermsListActivity.class );
            startActivity(intent);
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error updating assessment!")
                    .setMessage("Please be aware of the following:\n" +
                            "\tAll fields are required.\n" +
                            "\tThe Start Date cannot be equal to or after\n\tthe End Date.\n" +
                            "\tThe Goal Date must be in the specified\n\tformat(MM/dd/yyyy).\n")
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
     * Deletes the current assessment
     * @param view
     */
    public void deleteAssessment(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure to delete this assessment?")
                .setMessage("You will be redirected to the Terms List page on deletion.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Assessments assessmentDel = new Assessments(assessmentID, editTitle.getText().toString(),
                                editGoalStartDate.getText().toString(), editGoalEndDate.getText().toString(),
                                editType.getText().toString(), courseID);

                        schedulerRepository.delete(assessmentDel);

                        //Go back to the Term Lists screen
                        Intent intent=new Intent(AssessmentsDetailActivity.this, TermsListActivity.class );
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
