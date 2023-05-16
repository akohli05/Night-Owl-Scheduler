package com.example.nightowlscheduler.Database;

import android.content.Context;
import com.example.nightowlscheduler.DAO.*;
import com.example.nightowlscheduler.Entity.*;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Sets up the database
 *
 * @author Aimy Kohli
 */

@Database(entities={Terms.class, Courses.class, Assessments.class, Mentors.class, Notes.class}, version=6, exportSchema = false)
    public abstract class SchedulerDatabaseBuilder extends RoomDatabase {
        //DAOs
        public abstract TermsDAO termsDAO();
        public abstract CoursesDAO coursesDAO();
        public abstract AssessmentsDAO assessmentsDAO();
        public abstract MentorsDAO mentorsDAO();
        public abstract NotesDAO notesDAO();

        private static volatile SchedulerDatabaseBuilder INSTANCE;

        public static SchedulerDatabaseBuilder getDatabase(final Context context) {
            if(INSTANCE==null){
                synchronized (SchedulerDatabaseBuilder.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SchedulerDatabaseBuilder.class, "nightOwlSchedulerDatabase.db")
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
    }

