package com.gameshadow.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set up views
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        etEmail = findViewById(R.id.editTextEmailAddress);
        etPassword = findViewById(R.id.editTextPassword);
        Button SignIn = findViewById(R.id.sign_in);
        Button SignUp = findViewById(R.id.sign_up);

        SignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Get input
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // Validate input
                if(email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Authenticate with database
                //Users db = Users.getInstance(this);
                Users newuser = new Users().getEmail(Email);

                if(newuser == null) {
                    Toast.makeText(LoginActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                }
                else if(!newuser.getPassword().equals(password)) {
                    Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Login successful

                    // Launch main activity
                    Intent intent = new Intent(LoginActivity.this, NotesActivity.class);
                    startActivity(intent);
                    finish();
                }
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