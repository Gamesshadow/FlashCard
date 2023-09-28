package com.gameshadow.flashcard;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class Note implements Serializable {


    @PrimaryKey
    @ColumnInfo(name = "dateAdded")
    private String dateAdded;

    @ColumnInfo(name = "noteQuestion")
    private String noteQuestion;
    @ColumnInfo(name = "noteText")
    private String noteText;
    @ColumnInfo(name = "noteBG")
    private int noteBG;

    // Default constructor
    public Note() {
    }

    // Constructor
    public Note(@NonNull String dateAdded, String noteText,int noteBG,String noteQuestion) {
        this.dateAdded = dateAdded;
        this.noteText = noteText;
        this.noteBG = noteBG;
        this.noteQuestion = noteQuestion;
    }

    // Getters and setters
    @NonNull
    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(@NonNull String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public int getNoteBG() {
        return noteBG;
    }

    public String getNoteQuestion() {
        return noteQuestion;
    }

    public void setNoteQuestion(String noteQuestion) {
        this.noteQuestion = noteQuestion;
    }

    public void setNoteBG(int noteBG) {
        this.noteBG = noteBG;
    }


}
