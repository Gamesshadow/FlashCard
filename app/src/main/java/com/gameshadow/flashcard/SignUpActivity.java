package com.gameshadow.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class SignUpActivity extends AppCompatActivity {

    EditText FirstName, LastName, ParentEmail, etEmail, etPassword;
    Button SignUp;

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

        Button SignUp = findViewById(R.id.sign_up);

        SignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Set up strings to capture data
                String dateAdded = Note.getDateAdded();
                String FName = FirstName.getText().toString();
                String LName = LastName.getText().toString();
                String Email = etEmail.getText().toString();
                String PEmail = ParentEmail.getText().toString();
                String Password = etPassword.getText().toString();
                Boolean IsAdmin = false;

                //Validate data - No fields blank except PEmail
                if(FName.isEmpty() || LName.isEmpty() || Email.isEmpty() || Password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                //If PEmail == Email, = Parent
                if (PEmail == Email){ IsAdmin = true;}
                //if PEmail == null, = Parent
                if (PEmail == null){IsAdmin = true;}
                //If PEmail != Email, = Student
                if (PEmail != Email){IsAdmin = false;}


                // Create user object
                Users newUser = new Users(dateAdded, FName, LName, Email, PEmail, Password, (Boolean) IsAdmin);
                UserRepository.addUser(newUser);

                // Save to database
                //Users db = Users.getInstance(getApplicationContext());
                UserRepository.addUser(newUser);
                boolean success = true;


                if(success) {
                    Toast.makeText(SignUpActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();}


                // Launch Notes activity when done
                Intent intent = new Intent(SignUpActivity.this, NotesActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
