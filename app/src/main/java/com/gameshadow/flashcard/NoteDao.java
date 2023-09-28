package com.gameshadow.flashcard;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.lifecycle.LiveData;
import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addNote(Note note);

    @Query("SELECT * FROM notes ORDER BY dateAdded DESC")
    LiveData<List<Note>> getNotes();

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);
}