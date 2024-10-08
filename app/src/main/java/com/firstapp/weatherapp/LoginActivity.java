package com.firstapp.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(v -> {
            // Navigate to the RegisterActivity when the register button is clicked
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Check if the username and password are valid
            if (databaseHelper.checkUser(username, password)) {
                // Login successful
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
                finish();
            } else {
                // Login failed, display an error message
                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
