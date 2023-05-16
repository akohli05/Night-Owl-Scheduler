package com.example.nightowlscheduler.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.example.nightowlscheduler.Database.SchedulerRepository;
import com.example.nightowlscheduler.Entity.Assessments;
import com.example.nightowlscheduler.Entity.Courses;
import com.example.nightowlscheduler.Entity.Notes;
import com.example.nightowlscheduler.Entity.Terms;
import com.example.nightowlscheduler.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Set main title on toolbar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Welcome");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    /**
     * Go the main term list screen from menu
     * Go the main courses list screen from menu
     * Go the home screen from menu
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addsampledata:
                //Inserting sample terms
                SchedulerRepository repo = new SchedulerRepository(getApplication());
                Terms term = new Terms(10,"Term Winter","1/15/2022", "7/15/2022");
                repo.insert(term);
                Terms term2 = new Terms(11,"Summer Term","5/05/2022", "8/05/2022");
                repo.insert(term2);

                //Inserting sample courses and notes
                Courses course = new Courses(9, "Winter Course", "1/25/2022",
                            "2/15/2022", "Completed", 10 );
                repo.insert(course);
                Notes note = new Notes(9, "Test Note 1",
                        "The first test note", 9);
                repo.insert(note);

                Courses course1 = new Courses(10, "Winter Course 2", "2/25/2022",
                        "3/10/2022", "Completed", 10 );
                repo.insert(course1);
                Notes note1 = new Notes(10, "Test Note 2",
                        "The second test note", 10);
                repo.insert(note1);

                Courses course2 = new Courses(11, "Summer Course", "5/06/2022",
                        "6/10/2022", "Completed", 11 );
                repo.insert(course2);
                Notes note2 = new Notes(11, "Test Note 3",
                        "The third test note", 11);
                repo.insert(note2);

                //Sample Assessments
                Assessments assessment = new Assessments(9, "Winter Assessment",
                        "2/10/2022", "2/11/2022",
                        "Performance", 9);
                repo.insert(assessment);

                Assessments assessment1 = new Assessments(10, "Summer Assessment",
                        "6/09/2022", "6/10/2022",
                        "Objective", 11);
                repo.insert(assessment1);

                return true;
        }
        return true;
    }

    /**
     * Go to the Terms list screen
     * @param view The view
     */
    public void goToTermsListScreen(View view) {
        Intent intent=new Intent(MainActivity.this, TermsListActivity.class );
        startActivity(intent);

    }

}