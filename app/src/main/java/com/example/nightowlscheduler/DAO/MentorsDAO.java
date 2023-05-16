package com.example.nightowlscheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.nightowlscheduler.Entity.Mentors;

import java.util.List;

@Dao
public interface MentorsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Mentors mentors);

    @Update
    void update(Mentors mentors);

    @Delete
    void delete(Mentors mentors);

    @Query("SELECT * FROM Mentors ORDER BY mentorID ASC")
    List<Mentors> getAllMentors();

}
