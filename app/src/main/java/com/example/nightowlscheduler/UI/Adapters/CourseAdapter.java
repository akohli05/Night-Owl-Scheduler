package com.example.nightowlscheduler.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nightowlscheduler.Entity.Courses;
import com.example.nightowlscheduler.R;
import com.example.nightowlscheduler.UI.Activity.CoursesDetailActivity;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{
    class CourseViewHolder extends RecyclerView.ViewHolder{
        //Variables
        private final TextView courseTitleItemView;

        /**
         * Go to the course detail view once a specific course is clicked
         * @param itemView
         */
        private CourseViewHolder(View itemView){
            super(itemView);
            courseTitleItemView = itemView.findViewById(R.id.textViewCourseTitle);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Courses current = mCourses.get(position);
                    Intent intent = new Intent(context, CoursesDetailActivity.class);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("courseTitle",current.getCourseTitle());
                    intent.putExtra("courseStartDate", current.getCourseStartDate());
                    intent.putExtra("courseEndDate", current.getCourseEndDate());
                    intent.putExtra("courseStatus", current.getCourseStatus());
                    intent.putExtra("courseTermID", current.getTermID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Courses> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    public CourseAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.courses_list_cardview,parent,false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if(mCourses !=null){
            Courses current = mCourses.get(position);
            String title = current.getCourseTitle();
            holder.courseTitleItemView.setText(title);
        }
        else{
            holder.courseTitleItemView.setText("No courses title");
        }
    }
    public void setCourses(List<Courses> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mCourses != null){
            return mCourses.size();
        }
        else return 0;
    }
}
