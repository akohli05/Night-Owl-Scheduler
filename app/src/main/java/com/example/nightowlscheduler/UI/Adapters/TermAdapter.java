package com.example.nightowlscheduler.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nightowlscheduler.Entity.Terms;
import com.example.nightowlscheduler.R;
import com.example.nightowlscheduler.UI.Activity.TermsListDetailActivity;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder>{
    class TermViewHolder extends RecyclerView.ViewHolder{
        //Variables
        private final TextView termTitleItemView;
        private final TextView termDatesItemView;

        /**
         * Go to the term detail view once a specific term is clicked
         * @param itemView
         */
        private TermViewHolder(View itemView){
            super(itemView);
            termTitleItemView = itemView.findViewById(R.id.textViewTermTitle);
            termDatesItemView = itemView.findViewById(R.id.textViewTermDate);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Terms current = mTerms.get(position);
                    Intent intent = new Intent(context, TermsListDetailActivity.class);
                    intent.putExtra("id", current.getTermID());
                    intent.putExtra("title",current.getTermTitle());
                    intent.putExtra("startDate", current.getTermStartDate());
                    intent.putExtra("endDate", current.getTermEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Terms> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;
    public TermAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.terms_list_cardview,parent,false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms !=null){
            Terms current = mTerms.get(position);
            String title = current.getTermTitle();
            String startDate = current.getTermStartDate();
            String endDate = current.getTermEndDate();
            holder.termTitleItemView.setText(title);
            holder.termDatesItemView.setText(startDate + " - " + endDate);
        }
        else{
            holder.termTitleItemView.setText("No term title");
            holder.termDatesItemView.setText("No term dates");
        }
    }
    public void setTerms(List<Terms> terms){
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mTerms !=null){
            return mTerms.size();
        }
        else return 0;
    }
}
