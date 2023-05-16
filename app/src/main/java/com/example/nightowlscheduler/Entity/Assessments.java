package com.example.nightowlscheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Model class for Assessments
 *
 * @author Aimy Kohli
 */

@Entity(tableName = "assessments")
public class Assessments {
    //Variables
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;

    private String assessmentTitle;
    private String assessmentStartDate;
    private String assessmentEndDate;
    private String assessmentType;
    private int courseID;

    public Assessments(int assessmentID, String assessmentTitle, String assessmentStartDate,
                       String assessmentEndDate, String assessmentType, int courseID) {
        this.assessmentID = assessmentID;
        this.assessmentTitle = assessmentTitle;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentType = assessmentType;
        this.courseID = courseID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public void setAssessmentStartDate(String assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public String getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public int getCourseID() {
        return courseID;
    }

    /**
     * Assessment Validation
     * Checks for empty fields and improper dates entered
     * @return 'true' if valid or 'false' if invalid
     */
    public boolean assessmentValidation(){
        //Date format for checking
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        if (assessmentTitle.isEmpty() || assessmentStartDate.isEmpty() || assessmentEndDate.isEmpty()
                || assessmentType.isEmpty()) {
            return false;
        }
        try {
            //Checks to make sure the dates are in the correct format
            Date start = dateFormat.parse(assessmentStartDate);
            Date end = dateFormat.parse(assessmentEndDate);

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
