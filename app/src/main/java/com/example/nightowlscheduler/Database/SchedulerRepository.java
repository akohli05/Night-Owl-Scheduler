package com.example.nightowlscheduler.Database;

import android.app.Application;
import com.example.nightowlscheduler.DAO.*;
import com.example.nightowlscheduler.Entity.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository class
 *
 * @author Aimy Kohli
 */
public class SchedulerRepository {
    //Individual DAOs
    private TermsDAO mTermsDAO;
    private CoursesDAO mCoursesDAO;
    private AssessmentsDAO mAssessmentsDAO;
    private MentorsDAO mMentorsDAO;
    private NotesDAO mNotesDAO;

    //Lists
    private List<Terms> allTermsList;
    private List<Courses> allCoursesList;
    private List<Courses> allCoursesListByTerm;
    private List<Assessments> allAssessmentsList;
    private List<Mentors> allMentorsList;
    private List<Notes> allNotesList;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public SchedulerRepository(Application application){
        SchedulerDatabaseBuilder db = SchedulerDatabaseBuilder.getDatabase(application);
        mTermsDAO = db.termsDAO();
        mCoursesDAO = db.coursesDAO();
        mAssessmentsDAO = db.assessmentsDAO();
        mMentorsDAO = db.mentorsDAO();
        mNotesDAO = db.notesDAO();
    }

    //All methods for the Terms
    public List<Terms> getAllTerms(){
        databaseExecutor.execute(()->{
            allTermsList = mTermsDAO.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allTermsList;
    }
    public void insert(Terms terms){

        databaseExecutor.execute(()->{
            mTermsDAO.insert(terms);
        });
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }
    public void update(Terms terms){
        databaseExecutor.execute(()->{
            mTermsDAO.update(terms);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Terms terms){
        databaseExecutor.execute(()->{
            mTermsDAO.delete(terms);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //All methods for the Courses
    public List<Courses> getAllCourses(){
        databaseExecutor.execute(()->{
            allCoursesList = mCoursesDAO.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allCoursesList;
    }

    public void insert(Courses courses){
        databaseExecutor.execute(()->{
            mCoursesDAO.insert(courses);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Courses courses){
        databaseExecutor.execute(()->{
            mCoursesDAO.update(courses);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Courses courses){
        databaseExecutor.execute(()->{
            mCoursesDAO.delete(courses);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //All methods for the Assessments
    public List<Assessments> getAllAssessments(){
        databaseExecutor.execute(()->{
            allAssessmentsList = mAssessmentsDAO.getAllAssessments();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allAssessmentsList;
    }
    public void insert(Assessments assessments){
        databaseExecutor.execute(()->{
            mAssessmentsDAO.insert(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Assessments assessments){
        databaseExecutor.execute(()->{
            mAssessmentsDAO.update(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Assessments assessments){
        databaseExecutor.execute(()->{
            mAssessmentsDAO.delete(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //All methods for the Mentors
    public List<Mentors> getAllMentors(){
        databaseExecutor.execute(()->{
            allMentorsList = mMentorsDAO.getAllMentors();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allMentorsList;
    }
    public void insert(Mentors mentors){
        databaseExecutor.execute(()->{
            mMentorsDAO.insert(mentors);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Mentors mentors){
        databaseExecutor.execute(()->{
            mMentorsDAO.update(mentors);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Mentors mentors){
        databaseExecutor.execute(()->{
            mMentorsDAO.delete(mentors);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //All methods for the Notes
    public List<Notes> getAllNotes(){
        databaseExecutor.execute(()->{
            allNotesList = mNotesDAO.getAllNotes();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allNotesList;
    }
    public void insert(Notes notes){
        databaseExecutor.execute(()->{
            mNotesDAO.insert(notes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Notes notes){
        databaseExecutor.execute(()->{
            mNotesDAO.update(notes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Notes notes){
        databaseExecutor.execute(()->{
            mNotesDAO.delete(notes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
