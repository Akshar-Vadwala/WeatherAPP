package com.firstapp.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private Button profileButton;
    private TextView usernameLabel;
    private AWSIoTManager awsIoTManager;

    private TemperatureFragment temperatureFragment;
    private HumidityFragment humidityFragment;
    private PrecipitationFragment precipitationFragment;
    private PressureFragment pressureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usernameLabel = findViewById(R.id.username_label);
        String username = getIntent().getStringExtra("USERNAME");
        if (username != null) {
            usernameLabel.setText("Hello, " + username);
        }

        profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
        });

        temperatureFragment = new TemperatureFragment();
        humidityFragment = new HumidityFragment();
        precipitationFragment = new PrecipitationFragment();
        pressureFragment = new PressureFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_layout, temperatureFragment, "temperature")
                .add(R.id.fragment_layout, humidityFragment, "humidity").hide(humidityFragment)
                .add(R.id.fragment_layout, precipitationFragment, "precipitation").hide(precipitationFragment)
                .add(R.id.fragment_layout, pressureFragment, "pressure").hide(pressureFragment)
                .commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (savedInstanceState == null) {
            loadFragment(temperatureFragment);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.temperature) {
                selectedFragment = temperatureFragment;
            } else if (itemId == R.id.humidity) {
                selectedFragment = humidityFragment;
            } else if (itemId == R.id.precipitation) {
                selectedFragment = precipitationFragment;
            } else if (itemId == R.id.pressure) {
                selectedFragment = pressureFragment;
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }

            return true;
        });

        awsIoTManager = AWSIoTManager.getInstance(this);
        connectToAWS();
    }

    private void connectToAWS() {
        awsIoTManager.connect(isConnected -> {
            if (isConnected) {
                Log.d(TAG, "Connected to AWS IoT");
                subscribeToTopics();
            } else {
                Log.e(TAG, "Failed to connect to AWS IoT");
            }
        });
    }

    private void subscribeToTopics() {
        awsIoTManager.subscribeToTopic("esp8266/pub", data -> {
            Log.d(TAG, "Message received: " + data.toString());
            updateUI(data);
        });
    }

    private void updateUI(final JSONObject data) {
        runOnUiThread(() -> {
            try {
                double temperature = data.getDouble("temperature");
                double humidity = data.getDouble("humidity");
                int waterValue = data.getInt("water_value");
                double pressure = data.getDouble("pressure");
                double altitude = data.getDouble("altitude");
                double pressure_sea = data.getDouble("pressure_sea");

                if (temperatureFragment != null) {
                    temperatureFragment.updateTemperature(temperature);
                }
                if (humidityFragment != null) {
                    humidityFragment.updateHumidity(humidity);
                }
                if (precipitationFragment != null) {
                    precipitationFragment.updatePrecipitation(waterValue);
                }

                if(pressureFragment != null){
                    pressureFragment.updatePressure(pressure, altitude, pressure_sea);
                }

            } catch (JSONException e) {
                Log.e(TAG, "Error parsing JSON: " + e.getMessage());
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        hideAllFragmentsExcept(fragment);
    }

    private void hideAllFragmentsExcept(Fragment exceptFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (temperatureFragment != null && temperatureFragment != exceptFragment) {
            transaction.hide(temperatureFragment);
        }
        if (humidityFragment != null && humidityFragment != exceptFragment) {
            transaction.hide(humidityFragment);
        }
        if (precipitationFragment != null && precipitationFragment != exceptFragment) {
            transaction.hide(precipitationFragment);
        }
        if (pressureFragment != null && pressureFragment != exceptFragment) {
            transaction.hide(pressureFragment);
        }
        transaction.show(exceptFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
