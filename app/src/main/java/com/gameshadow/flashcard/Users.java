package com.gameshadow.flashcard;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "users")
public class Users implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "dateAdded")
    private String dateAdded;
    @ColumnInfo(name = "FirstName")
    private String FirstName;
    @ColumnInfo(name = "LastName")
    private String LastName;
    @ColumnInfo(name = "Email")
    private String Email;
    @ColumnInfo(name = "Parent_Email")
    private String ParentEmail;
    @ColumnInfo(name = "Password")
    private String Password;
    @ColumnInfo(name = "isAdmin")
    private static boolean IsAdmin;

    // Default constructor
    public Users() {
    }
    // Constructor
    public Users(String dateAdded, String FirstName, String LastName, String Email, String ParentEmail, String Password,boolean isAdmin) {
        this.dateAdded = dateAdded;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Password = Password;
        this.IsAdmin = isAdmin;
        this.ParentEmail = ParentEmail;
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

    public String getParentEmail() {
        return ParentEmail;
    }
    public void setParentEmail(String ParentEmail) {
        this.ParentEmail = ParentEmail;
    }

    public String getPassword() {
        return Password;
    }
    public void setPassword(String Password) {
        this.Password = Password;
    }

    public static boolean getIsAdmin() {
        return IsAdmin;
    }
    public void setIsAdmin(boolean isAdmin) {
        this.IsAdmin = isAdmin;
    }
}