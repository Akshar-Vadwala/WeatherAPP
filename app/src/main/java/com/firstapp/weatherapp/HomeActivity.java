package com.firstapp.weatherapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set default fragment (optional, loads HumidityFragment on startup)
        if (savedInstanceState == null) {
            loadFragment(new TemperatureFragment()); // Load humidity fragment by default
        }

        // Handle bottom navigation item selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Using if-else to avoid 'constant expression required' error
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
