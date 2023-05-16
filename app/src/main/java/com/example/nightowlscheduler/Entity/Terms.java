package com.example.nightowlscheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Entity class for Terms
 *
 * @author Aimy Kohli
 */

@Entity(tableName = "terms")
public class Terms {
    //Variables
    @PrimaryKey(autoGenerate = true)
    int termID;

    String termTitle;
    String termStartDate;
    String termEndDate;

    public Terms(int termID, String termTitle, String termStartDate, String termEndDate) {
        this.termID = termID;
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public void setTermStartDate(String termStartDate) {
        this.termStartDate = termStartDate;
    }

    public void setTermEndDate(String termEndDate) {
        this.termEndDate = termEndDate;
    }

    public int getTermID() {
        return termID;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public String getTermStartDate() {
        return termStartDate;
    }

    public String getTermEndDate() {
        return termEndDate;
    }

    /**
     * Term Validation
     * Checks for empty fields and improper dates entered
     * @return 'true' if valid or 'false' if invalid
     */
    public boolean termValidation(){
        //Date format for checking
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        if (termTitle.isEmpty() || termStartDate.isEmpty() || termEndDate.isEmpty()) {
            return false;
        }
        try {
            //Checks to make sure the dates are in the correct format
            Date start = dateFormat.parse(termStartDate);
            Date end = dateFormat.parse(termEndDate);
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
