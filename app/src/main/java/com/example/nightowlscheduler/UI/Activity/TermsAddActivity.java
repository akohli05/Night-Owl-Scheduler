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
import com.example.nightowlscheduler.Entity.Terms;
import com.example.nightowlscheduler.R;

public class TermsAddActivity extends AppCompatActivity {
    //Repository
    SchedulerRepository repo;

    //Variables
    EditText addTitle;
    EditText addStartDate;
    EditText addEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_term);

        //Set title on toolbar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Display back arrow
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);//Change default back arrow to my drawable
        getSupportActionBar().setTitle("Add Term");

        addTitle = findViewById(R.id.addTermTitle);
        addStartDate = findViewById(R.id.addTermStartDate);
        addEndDate = findViewById(R.id.addTermEndDate);

        repo = new SchedulerRepository(getApplication());
    }

    /**
     * Saves the new term
     * @param view
     */
    public void saveNewTerm(View view) {
        //New term ID
        //int newID = repo.getAllTerms().get(repo.getAllTerms().size() - 1).getTermID() + 1;
        int newID = repo.getAllTerms().size() + 1;

        //Term to add
        Terms term = new Terms(newID, addTitle.getText().toString(),
                addStartDate.getText().toString(), addEndDate.getText().toString());

        boolean isValid = term.termValidation();//validate the term
        if(isValid) {
            repo.insert(term);
            Toast.makeText(getApplicationContext(),"Add Successful - Redirecting to Terms List",Toast.LENGTH_LONG).show();

            //Go back to Term Lists screen
            Intent intent=new Intent(TermsAddActivity.this, TermsListActivity.class );
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
     * Cancel new term
     * Go back to the Term Lists screen
     * @param view The view
     */
    public void cancelNewTerm(View view) {
        Toast.makeText(getApplicationContext(),"Cancel Successful - Redirecting to Terms List",Toast.LENGTH_LONG).show();

        Intent intent=new Intent(TermsAddActivity.this, TermsListActivity.class );
        startActivity(intent);
    }

}
