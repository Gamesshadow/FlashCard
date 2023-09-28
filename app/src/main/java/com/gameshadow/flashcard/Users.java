package com.gameshadow.flashcard;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "users")
public class Users implements Serializable {


    @PrimaryKey
    @ColumnInfo(name = "dateAdded")
    private String dateAdded;

    @ColumnInfo(name = "FirstName")
    private String FirstName;
    @ColumnInfo(name = "LastName")
    private String LastName;
    @ColumnInfo(name = "Email")
    private String Email;
    @ColumnInfo(name = "Password")
    private String Password;
    @ColumnInfo(name = "isAdmin")
    private boolean isAdmin;

    // Default constructor
    public Users() {
    }
    // Constructor
    public Users(@NonNull String dateAdded, String FirstName, String Lastname, String Email, String Password,boolean isAdmin) {
        this.dateAdded = dateAdded;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Password = Password;
        this.isAdmin = isAdmin;
    }

    // Getters and setters
    @NonNull
    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(@NonNull String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }
    public void setPassword(String Password) {
        this.Password = Password;
    }

    public boolean getisAdmin() {
        return isAdmin;
    }
    public void setisAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}
