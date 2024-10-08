package com.firstapp.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);

        EditText emailEditText = findViewById(R.id.email);
        EditText phoneEditText = findViewById(R.id.phone);
        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {
                // Insert data into SQLite database
                boolean isInserted = databaseHelper.insertData(username, password, email, phone);
                if (isInserted) {
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    finish(); 
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
