package com.example.nightowlscheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class for Mentors
 *
 * @author Aimy Kohli
 */

@Entity(tableName = "mentors")
public class Mentors {
    //Variables
    @PrimaryKey(autoGenerate = true)
    int mentorID;

    String mentorName;
    String phone;
    String email;
    int courseID;

    public Mentors(int mentorID, String mentorName, String phone, String email, int courseID) {
        this.mentorID = mentorID;
        this.mentorName = mentorName;
        this.phone = phone;
        this.email = email;
        this.courseID = courseID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getMentorID() {
        return mentorID;
    }

    public String getMentorName() {
        return mentorName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getCourseID() {
        return courseID;
    }

    /**
     * Mentor Validation
     * Checks for empty fields
     * @return 'true' if valid or 'false' if invalid
     */
    public boolean termValidation(){
        if (mentorName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            return false;
        }
        return true;
    }
}
