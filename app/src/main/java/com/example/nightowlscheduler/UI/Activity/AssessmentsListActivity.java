package com.example.nightowlscheduler.UI.Activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nightowlscheduler.Database.SchedulerRepository;
import com.example.nightowlscheduler.Entity.Assessments;
import com.example.nightowlscheduler.R;
import com.example.nightowlscheduler.UI.Adapters.AssessmentsAdapter;

import java.util.ArrayList;
import java.util.List;

public class AssessmentsListActivity extends AppCompatActivity {

    int courseID;
    SchedulerRepository schedulerRepository;
    private AssessmentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessments_list);

        //Set title on toolbar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Assessments List");

        courseID = getIntent().getIntExtra("courseID", -1);

        //Recycler view
        RecyclerView assessmentRV = findViewById(R.id.recycler_view_assessments);

        schedulerRepository = new SchedulerRepository(getApplication());
        List<Assessments> filteredAssessments = new ArrayList<>();
        for(Assessments assessment: schedulerRepository.getAllAssessments()){
            if(assessment.getCourseID() == courseID){
                filteredAssessments.add(assessment);
            }
        }
        adapter = new AssessmentsAdapter(this);
        assessmentRV.setLayoutManager(new LinearLayoutManager(this));
        assessmentRV.setAdapter(adapter);
        adapter.setAssessments(filteredAssessments);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.courses_list_menu, menu);
        return true;
    }

    /**
     * Go the main term list screen from menu
     * Go the home screen from menu
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gohomepage:
                Intent intent=new Intent(AssessmentsListActivity.this, MainActivity.class );
                startActivity(intent);
                return true;

            case R.id.gototermslist:
                Intent intent1=new Intent(AssessmentsListActivity.this, TermsListActivity.class );
                startActivity(intent1);
                return true;
        }
        return true;
    }

    /**
     * Go to the Add Assessment screen
     * @param view The view
     */
    public void goToAddAssessmentScreen(View view) {
        Intent intent=new Intent(AssessmentsListActivity.this, AssessmentsAddActivity.class );
        intent.putExtra("courseID",courseID);
        startActivity(intent);
    }

}
