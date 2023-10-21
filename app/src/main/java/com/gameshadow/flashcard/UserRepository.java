package com.gameshadow.flashcard;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;


import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private static UserDao userDao;
    private LiveData<List<Users>> allUsers;
    static ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    public UserRepository(Context context) {

        NoteDatabase database = NoteDatabase.getDatabase(context);
        userDao = database.userDao();
        allUsers = userDao.getAllUsers();
    }
    public LiveData<List<Users>> getAllUsers() {
        return allUsers;
    }

    public static void addUser(Context context, Users user) {
        NoteDatabase database = NoteDatabase.getDatabase(context); {
            executor.execute(() -> {
                //Background work here
                userDao.insert(user);
            });
        }
    }
    public LiveData<Users> getUserByEmail(String email, String password) {
        return userDao.getUserByEmail(email, password);
    }
}