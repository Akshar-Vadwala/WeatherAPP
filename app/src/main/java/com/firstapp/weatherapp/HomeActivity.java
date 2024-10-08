package com.firstapp.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView; // Import TextView
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private Button profileButton;
    private TextView usernameLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        usernameLabel = findViewById(R.id.username_label);

        // Get the username from the Intent
        String username = getIntent().getStringExtra("USERNAME");

        // Set the username in the TextView
        if (username != null) {
            usernameLabel.setText("Hello, " + username);
        }

        // Navigate to user profile
        profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
        });

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Load the default fragment
        if (savedInstanceState == null) {
            loadFragment(new TemperatureFragment());
        }

        // Handle bottom navigation item selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.temperature) {
                selectedFragment = new TemperatureFragment();
            } else if (itemId == R.id.humidity) {
                selectedFragment = new HumidityFragment();
            } else if (itemId == R.id.precipitation) {
                selectedFragment = new PrecipitationFragment();
            } else if (itemId == R.id.pressure) {
                selectedFragment = new PressureFragment();
            }

            // Load the selected fragment
            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }

            return true;
        });
    }

    // Helper method to load fragments into the FrameLayout
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, fragment)
                .commit();
    }
}
