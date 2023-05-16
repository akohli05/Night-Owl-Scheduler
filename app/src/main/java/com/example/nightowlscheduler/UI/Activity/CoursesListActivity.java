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
import com.example.nightowlscheduler.Entity.Courses;
import com.example.nightowlscheduler.R;
import com.example.nightowlscheduler.UI.Adapters.CourseAdapter;

import java.util.ArrayList;
import java.util.List;

public class CoursesListActivity extends AppCompatActivity {

    int termID, courseTermID;
    SchedulerRepository schedulerRepository;
    private CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_list);

        //Set title on toolbar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Courses List");

        termID = getIntent().getIntExtra("id", -1);

        //Recycler view
        RecyclerView courseRV = findViewById(R.id.recycler_view_course);

        schedulerRepository = new SchedulerRepository(getApplication());
        List<Courses> filteredCourses = new ArrayList<>();
        for(Courses course: schedulerRepository.getAllCourses()){
            if(course.getTermID() == termID){
                filteredCourses.add(course);
            }
        }
        adapter = new CourseAdapter(this);
        courseRV.setLayoutManager(new LinearLayoutManager(this));
        courseRV.setAdapter(adapter);
        adapter.setCourses(filteredCourses);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.courses_list_menu, menu);
        return true;
    }

    /**
     * Go the main term list screen from menu
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gohomepage:
                Intent intent=new Intent(CoursesListActivity.this, MainActivity.class );
                startActivity(intent);
                return true;

            case R.id.gototermslist:
                Intent intent1=new Intent(CoursesListActivity.this, TermsListActivity.class );
                startActivity(intent1);
                return true;
        }
        return true;
    }

    /**
     * Go to the Add Course screen
     * @param view The view
     */
    public void goToAddCourseScreen(View view) {
        Intent intent=new Intent(CoursesListActivity.this, CoursesAddActivity.class );
        intent.putExtra("id",termID);
        startActivity(intent);
    }

}
