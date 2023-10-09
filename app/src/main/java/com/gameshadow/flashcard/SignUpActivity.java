package com.gameshadow.flashcard;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    EditText FirstName, LastName, ParentEmail, etEmail, etPassword;
    Button SignUp;
    TextView close_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set up views
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        FirstName = findViewById(R.id.editTextFirstName);
        LastName = findViewById(R.id.editTextLastName);
        etEmail = findViewById(R.id.editTextEmailAddress);
        etPassword = findViewById(R.id.editTextPassword);
        ParentEmail = findViewById(R.id.editTextParentEmailAddress);
        close_Button = findViewById(R.id.close_Button);

        SignUp = findViewById(R.id.sign_up);


        close_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Set up strings to capture data
                String dateAdded = ""; //Note.getDateAdded();
                String FName = FirstName.getText().toString();
                String LName = LastName.getText().toString();
                String Email = etEmail.getText().toString();
                String PEmail = ParentEmail.getText().toString();
                String Password = etPassword.getText().toString();
                boolean IsAdmin = false;

                //Validate data - No fields blank except PEmail
                if(FName.isEmpty() || LName.isEmpty() || Email.isEmpty() || Password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                //If PEmail == Email, = Parent
                if (PEmail == null || PEmail == Email){ IsAdmin = true;}
                //else IsAdmin == false

                // Create user object
                Users newUser = new Users(dateAdded, FName, LName, Email, PEmail, Password, IsAdmin);
                // Save to database
                new UserRepository(getBaseContext()).addUser(getBaseContext(), newUser);

                Toast.makeText(SignUpActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();

                // Launch Notes activity when done
                Intent intent = new Intent(SignUpActivity.this, NotesActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}