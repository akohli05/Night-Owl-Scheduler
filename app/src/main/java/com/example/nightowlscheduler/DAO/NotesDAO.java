package com.example.nightowlscheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.nightowlscheduler.Entity.Notes;

import java.util.List;

@Dao
public interface NotesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Notes notes);

    @Update
    void update(Notes notes);

    @Delete
    void delete(Notes notes);

    @Query("SELECT * FROM Notes ORDER BY noteID ASC")
    List<Notes> getAllNotes();

}
