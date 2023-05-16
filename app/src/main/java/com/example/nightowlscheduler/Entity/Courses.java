package com.example.nightowlscheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Entity class for Courses
 *
 * @author Aimy Kohli
 */

@Entity(tableName = "courses")
public class Courses {
    //Variables
    @PrimaryKey(autoGenerate = true)
    private int courseID;

    private String courseTitle;
    private String courseStartDate;
    private String courseEndDate;
    private String courseStatus;
    private int termID;

    public Courses(int courseID, String courseTitle, String courseStartDate, String courseEndDate, String courseStatus, int termID) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.termID = termID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public int getTermID() {
        return termID;
    }

    /**
     * Course Validation
     * Checks for empty fields and improper dates entered
     * @return 'true' if valid or 'false' if invalid
     */
    public boolean courseValidation(){
        //Date format for checking
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        if (courseTitle.isEmpty() || courseStartDate.isEmpty()
                || courseEndDate.isEmpty() || courseStatus.isEmpty()) {
            return false;
        }
        try {
            //Checks to make sure the dates are in the correct format
            Date start = dateFormat.parse(courseStartDate);
            Date end = dateFormat.parse(courseEndDate);
            //Checks to make sure that the start date is before the end date
            if (!start.before(end)) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
