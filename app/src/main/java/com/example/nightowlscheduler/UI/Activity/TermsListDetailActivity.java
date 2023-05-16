package com.example.nightowlscheduler.UI.Activity;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nightowlscheduler.Database.SchedulerRepository;
import com.example.nightowlscheduler.Entity.Courses;
import com.example.nightowlscheduler.Entity.Terms;
import com.example.nightowlscheduler.R;
import com.example.nightowlscheduler.UI.Adapters.CourseAdapter;

import java.util.ArrayList;
import java.util.List;

public class TermsListDetailActivity extends AppCompatActivity {
    //Variables
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;

    String title;
    String startDate;
    String endDate;
    int termID;
    SchedulerRepository schedulerRepository;
    private CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_detail);

        //Set title on toolbar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Display back arrow
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);//Change default back arrow to my drawable
        getSupportActionBar().setTitle("Term Details");

        //Edit term
        editTitle = findViewById(R.id.editTermTitle);
        editStartDate = findViewById(R.id.editTermStartDate);
        editEndDate = findViewById(R.id.editTermEndDate);

        termID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");

        editTitle.setText(title);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

        schedulerRepository = new SchedulerRepository(getApplication());
    }

    /**
     * Updates the current term
     * @param view
     */
    public void updateTerm(View view) {
        Terms term = new Terms(termID, editTitle.getText().toString(),
                editStartDate.getText().toString(), editEndDate.getText().toString());

        boolean isValid = term.termValidation();//validate the term
        if(isValid) {
            schedulerRepository.update(term);

            //Update confirmation
            Toast.makeText(getApplicationContext(),"Update Successful - Redirecting to Terms List",Toast.LENGTH_LONG).show();

            //Go back to the Term Lists screen
            Intent intent=new Intent(TermsListDetailActivity.this, TermsListActivity.class );
            startActivity(intent);
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error adding term!")
                    .setMessage("Please be aware of the following:\n" +
                            "\tAll fields are required.\n" +
                            "\tThe Start Date cannot be equal to or after\n\tthe End Date\n" +
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
     * Deletes the current term
     * @param view
     */
    public void deleteTerm(View view) {
        //makes sure there are no courses associate with the term
        List<Courses> allRelatedCourses = new ArrayList<>();
        for(Courses course: schedulerRepository.getAllCourses()){
            if(course.getTermID() == termID){
                allRelatedCourses.add(course);
            }
        }

        if(allRelatedCourses.isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure to delete this term?")
                    .setMessage("You will be redirected to the Terms List page on deletion.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Terms term = new Terms(termID, editTitle.getText().toString(),
                                    editStartDate.getText().toString(), editEndDate.getText().toString());
                            schedulerRepository.delete(term);

                            //Go back to the Term Lists screen
                            Intent intent = new Intent(TermsListDetailActivity.this, TermsListActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what should happen when negative button is clicked
                            Toast.makeText(getApplicationContext(), "Delete Cancelled", Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();
        }
        //error box - can't delete course
        else{
            AlertDialog alertDialog1 = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Cannot delete term!")
                    .setMessage("This term contains courses and\ncannot be deleted.")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //nothing happens
                        }
                    })
                    .show();
        }

    }

    /**
     * Go to the Course List screen
     * @param view The view
     */
    public void goToCoursesListScreen(View view) {
        Intent intent=new Intent(TermsListDetailActivity.this, CoursesListActivity.class );
        intent.putExtra("id",termID);
        startActivity(intent);
    }

}
