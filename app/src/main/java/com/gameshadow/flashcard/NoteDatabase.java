package com.gameshadow.flashcard;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        entities = {Note.class, Users.class},
        version = 2
)
@TypeConverters(NoteConverters.class)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();
    public abstract UserDao userDao();
    private static volatile NoteDatabase INSTANCE;

    public static NoteDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (NoteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static NoteDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(
                        context.getApplicationContext(),
                        NoteDatabase.class,
                        "notes_database"
                )
                .build();
    }
}
