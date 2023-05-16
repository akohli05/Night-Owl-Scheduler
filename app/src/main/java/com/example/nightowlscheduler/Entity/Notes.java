package com.example.nightowlscheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Model class for Notes
 *
 * @author Aimy Kohli
 */

@Entity(tableName = "notes")
public class Notes {
    //Variables
    @PrimaryKey(autoGenerate = true)
    int noteID;

    String noteTitle;
    String noteContent;
    int courseID;

    public Notes(int noteID, String noteTitle, String noteContent, int courseID) {
        this.noteID = noteID;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.courseID = courseID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getNoteID() {
        return noteID;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public int getCourseID() {
        return courseID;
    }

    /**
     * Notes Validation
     * Checks for empty fields
     * @return 'true' if valid or 'false' if invalid
     */
    public boolean noteValidation(){
        if (noteTitle.isEmpty() || noteContent.isEmpty()) {
            return false;
        }
        return true;
    }
}
