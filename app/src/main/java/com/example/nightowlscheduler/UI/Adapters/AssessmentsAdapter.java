package com.example.nightowlscheduler.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nightowlscheduler.Entity.Assessments;
import com.example.nightowlscheduler.R;
import com.example.nightowlscheduler.UI.Activity.AssessmentsDetailActivity;

import java.util.List;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.AssessmentsViewHolder>{
    class AssessmentsViewHolder extends RecyclerView.ViewHolder{
        //Variables
        private final TextView assessmentTitleItemView;

        /**
         * Go to the assessment detail view once a specific assessment is clicked
         * @param itemView
         */
        private AssessmentsViewHolder(View itemView){
            super(itemView);
            assessmentTitleItemView = itemView.findViewById(R.id.textViewAssessmentTitle);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessments current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentsDetailActivity.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("assessmentTitle",current.getAssessmentTitle());
                    intent.putExtra("assessmentGoalStartDate", current.getAssessmentStartDate());
                    intent.putExtra("assessmentGoalEndDate", current.getAssessmentEndDate());
                    intent.putExtra("assessmentType", current.getAssessmentType());
                    intent.putExtra("courseID", current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Assessments> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;
    public AssessmentsAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public AssessmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessments_cardview,parent,false);
        return new AssessmentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentsViewHolder holder, int position) {
        if(mAssessments !=null){
            Assessments current = mAssessments.get(position);
            String title = current.getAssessmentTitle();
            holder.assessmentTitleItemView.setText(title);
        }
        else{
            holder.assessmentTitleItemView.setText("No assessment title");
        }
    }
    public void setAssessments(List<Assessments> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mAssessments != null){
            return mAssessments.size();
        }
        else return 0;
    }
}
