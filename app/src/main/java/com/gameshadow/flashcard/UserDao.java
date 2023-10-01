package com.gameshadow.flashcard;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Users user);

    @Query("SELECT * FROM users WHERE Email = :email and Password = :password")
    LiveData<Users> getUserByEmail(String email, String password);

    @Query("SELECT * FROM users")
    LiveData<List<Users>> getAllUsers();
}