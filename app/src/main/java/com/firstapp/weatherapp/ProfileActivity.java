package com.firstapp.weatherapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText usernameValue;
    private EditText passwordValue;
    private EditText emailValue;
    private EditText phoneValue;
    private DatabaseHelper databaseHelper;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize the EditText views
        usernameValue = findViewById(R.id.username_value);
        passwordValue = findViewById(R.id.password_value);
        emailValue = findViewById(R.id.email_value);
        phoneValue = findViewById(R.id.phone_value);
        Button logoutButton = findViewById(R.id.logout_button);
        Button backButton = findViewById(R.id.back_button);
        Button saveButton = findViewById(R.id.save_button);


        usernameValue.setEnabled(false);

        // Get the logged-in user's username from Intent extras
        username = getIntent().getStringExtra("USERNAME");

        databaseHelper = new DatabaseHelper(this);

        loadUserData();


        logoutButton.setOnClickListener(v -> {
            // Handle logout logic
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });


        backButton.setOnClickListener(v -> {
            // Navigate back to HomeActivity
            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
            finish();
        });

        // Set save button click listener to update the profile
        saveButton.setOnClickListener(v -> {
            // Get updated values from EditText fields
            String updatedPassword = passwordValue.getText().toString();
            String updatedEmail = emailValue.getText().toString();
            String updatedPhone = phoneValue.getText().toString();

            // Update user data in the database
            boolean isUpdated = databaseHelper.updateUserData(username, updatedPassword, updatedEmail, updatedPhone);

            if (isUpdated) {
                Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to load user data from the database and set it in EditText fields
    private void loadUserData() {
        Cursor cursor = databaseHelper.getUserData(username);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String password = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_3));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_4));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_5));

                // Set the values in the EditTexts
                usernameValue.setText(username);
                passwordValue.setText(password);
                emailValue.setText(email);
                phoneValue.setText(phone);
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Error retrieving user data", Toast.LENGTH_SHORT).show();
        }
    }
}
