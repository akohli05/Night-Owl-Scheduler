package com.example.nightowlscheduler.UI.Activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.example.nightowlscheduler.Database.SchedulerRepository;
import com.example.nightowlscheduler.Entity.Mentors;
import com.example.nightowlscheduler.R;

import java.util.ArrayList;
import java.util.List;

public class MentorsListActivity extends AppCompatActivity {
    //Variables
    TextView nameDetail;
    TextView phoneDetail;
    TextView emailDetail;

    String name;
    String phone;
    String email;

    int courseID;
    SchedulerRepository schedulerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mentor_display);

        //Set title on toolbar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Course Mentor");

        courseID = getIntent().getIntExtra("courseID", -1);

        //Get the mentor details text views
        nameDetail = findViewById(R.id.tvMentorNameDetail);
        phoneDetail = findViewById(R.id.tvMentorPhoneDetail);
        emailDetail = findViewById(R.id.tvMentorEmailDetail);

        schedulerRepository = new SchedulerRepository(getApplication());
        List<Mentors> filteredMentors = new ArrayList<>();
        for(Mentors mentor: schedulerRepository.getAllMentors()){
            if(mentor.getCourseID() == courseID){
                filteredMentors.add(mentor);
            }
        }
        name = filteredMentors.get(0).getMentorName();
        phone = filteredMentors.get(0).getPhone();
        email = filteredMentors.get(0).getEmail();

        //Set values
        nameDetail.setText(name);
        phoneDetail.setText(phone);
        emailDetail.setText(email);

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
                Intent intent=new Intent(MentorsListActivity.this, MainActivity.class );
                startActivity(intent);
                return true;

            case R.id.gototermslist:
                Intent intent1=new Intent(MentorsListActivity.this, TermsListActivity.class );
                startActivity(intent1);
                return true;
        }
        return true;
    }

}
