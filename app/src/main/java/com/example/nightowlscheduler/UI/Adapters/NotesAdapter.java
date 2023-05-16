package com.example.nightowlscheduler.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nightowlscheduler.Entity.Notes;
import com.example.nightowlscheduler.R;
import com.example.nightowlscheduler.UI.Activity.NotesDetailActivity;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{
    class NotesViewHolder extends RecyclerView.ViewHolder{
        //Variables
        private final TextView noteTitleItemView;

        /**
         * Go to the note detail view once a specific note is clicked
         * @param itemView
         */
        private NotesViewHolder(View itemView){
            super(itemView);
            noteTitleItemView = itemView.findViewById(R.id.textViewNoteTitle);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Notes current = mNotes.get(position);
                    Intent intent = new Intent(context, NotesDetailActivity.class);
                    intent.putExtra("noteID", current.getNoteID());
                    intent.putExtra("noteTitle",current.getNoteTitle());
                    intent.putExtra("noteMessage", current.getNoteContent());
                    intent.putExtra("courseID", current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Notes> mNotes;
    private final Context context;
    private final LayoutInflater mInflater;
    public NotesAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.notes_cardview,parent,false);
        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        if(mNotes !=null){
            Notes current = mNotes.get(position);
            String title = current.getNoteTitle();
            holder.noteTitleItemView.setText(title);
        }
        else{
            holder.noteTitleItemView.setText("No note title");
        }
    }
    public void setNotes(List<Notes> notes){
        mNotes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mNotes != null){
            return mNotes.size();
        }
        else return 0;
    }
}
