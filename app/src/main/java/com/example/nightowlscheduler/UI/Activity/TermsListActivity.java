package com.example.nightowlscheduler.UI.Activity;

import android.content.Intent;
import android.view.View;
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
import com.example.nightowlscheduler.UI.Adapters.TermAdapter;

import java.util.List;

public class TermsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_list);

        //Set title on toolbar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Display back arrow
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);//Change default back arrow to my drawable
        getSupportActionBar().setTitle("Terms");

        //Terms Recycler view
        RecyclerView termRV = findViewById(R.id.recycler_view_term);

        SchedulerRepository repo = new SchedulerRepository(getApplication());
        List<Terms> terms = repo.getAllTerms();
        final TermAdapter adapter = new TermAdapter(this);
        termRV.setLayoutManager(new LinearLayoutManager(this));
        termRV.setAdapter(adapter);
        adapter.setTerms(terms);

    }

    /**
     * Go to the Add Term  screen
     * @param view The view
     */
    public void goToAddTermScreen(View view) {
        Intent intent=new Intent(TermsListActivity.this, TermsAddActivity.class );
        startActivity(intent);
    }

}
