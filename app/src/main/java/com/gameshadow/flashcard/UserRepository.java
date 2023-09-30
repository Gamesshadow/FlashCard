package com.gameshadow.flashcard;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;


public class UserRepository {
    private static UserDao userDao;
    private LiveData<List<Users>> allUsers;

    public UserRepository(Context context) {
        NoteDatabase database = NoteDatabase.getDatabase(context);
        userDao = (UserDao) database.noteDao();
        allUsers = userDao.getAllUsers();
    }
    public LiveData<List<Users>> getAllUsers() {
        return allUsers;
    }

    public static Context addUser(Users user) {
        NoteDatabase database = NoteDatabase.getDatabase(addUser(user)); {
            userDao.insert(user);
        };
        return null;
    }
    public LiveData<Users> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }
}

