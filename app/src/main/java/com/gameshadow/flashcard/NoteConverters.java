package com.gameshadow.flashcard;
import androidx.room.TypeConverter;
import java.util.Date;

public class NoteConverters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value != null ? new Date(value) : null;
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date != null ? date.getTime() : null;
    }
}