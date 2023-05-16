package com.example.nightowlscheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.nightowlscheduler.Entity.Terms;

import java.util.List;

@Dao
public interface TermsDAO {
    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Terms term);

    @Update
    void update(Terms terms);

    @Delete
    void delete(Terms terms);

    @Query("SELECT * FROM TERMS ORDER BY termID ASC")
    List<Terms> getAllTerms();
}

