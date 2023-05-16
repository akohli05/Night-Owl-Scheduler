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
import com.example.nightowlscheduler.R;

public class AssessmentsAddActivity extends AppCompatActivity {
    //Repository
    SchedulerRepository repo;

    //Variables
    EditText addTitle;
    EditText addGoalStartDate;
    EditText addGoalEndDate;
    EditText addType;

    static int courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_assessment);

        //Set title on toolbar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Add Assessment");

        addTitle = findViewById(R.id.addAssessmentTitle);
        addGoalStartDate = findViewById(R.id.addAssessmentGoalStart);
        addGoalEndDate = findViewById(R.id.addAssessmentGoalEnd);
        addType = findViewById(R.id.addAssessmentType);

        courseID = getIntent().getIntExtra("courseID", -1);
        repo = new SchedulerRepository(getApplication());
    }

    /**
     * Saves the new assessment
     * @param view
     */
    public void saveNewAssessment(View view) {
        //New assessment ID
        int newID = repo.getAllAssessments().size() + 1;

        //assessment to add
        Assessments assessment = new Assessments(newID, addTitle.getText().toString(),
                addGoalStartDate.getText().toString(), addGoalEndDate.getText().toString(),
                addType.getText().toString(), courseID);
        boolean isValid = assessment.assessmentValidation();//validate the assessment

        if(isValid) {
            repo.insert(assessment);

            //Update confirmation
            Toast.makeText(getApplicationContext(),"Add Successful - Redirecting to Terms List",Toast.LENGTH_LONG).show();

            //Go back to Term list screen
            Intent intent = new Intent(AssessmentsAddActivity.this, TermsListActivity.class );
            startActivity(intent);
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error adding assessment!")
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
     * Cancel new assessment
     * Go back to the Assessments List screen
     * @param view The view
     */
    public void cancelNewAssessment(View view) {
        //Update confirmation
        Toast.makeText(getApplicationContext(),"Cancel Successful - Redirecting to Assessments List",Toast.LENGTH_LONG).show();

        Intent intent=new Intent(AssessmentsAddActivity.this, AssessmentsListActivity.class );
        intent.putExtra("courseID",courseID);
        startActivity(intent);
    }

}
