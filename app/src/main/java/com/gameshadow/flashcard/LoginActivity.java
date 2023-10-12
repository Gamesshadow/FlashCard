package com.gameshadow.flashcard;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LiveData;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button SignIn, SignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set up views
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        etEmail = findViewById(R.id.editTextEmailAddress);
        etPassword = findViewById(R.id.editTextPassword);
        AppCompatButton SignIn = findViewById(R.id.sign_in);
        AppCompatButton SignUp = findViewById(R.id.sign_up);

        SignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Get input
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                Intent parentIntent = new Intent(LoginActivity.this, ParentNotes.class);
                Intent studentIntent = new Intent(LoginActivity.this, StudentNotes.class);

                // Validate input
                if(email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Authenticate with database
                LiveData<Users> users = new UserRepository(LoginActivity.this).getUserByEmail(email, password);
                users.observe(LoginActivity.this, foundUser -> {

                    if(foundUser == null) {
                        Toast.makeText(LoginActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                    }
                    else if(!foundUser.getPassword().equals(password)) {
                        Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // Login successful
                        // Launch main activity
                        if (Users.getIsAdmin()){startActivity(parentIntent);}
                        else {startActivity(studentIntent);}
                    }
                });
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Launch Sign Up activity
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}