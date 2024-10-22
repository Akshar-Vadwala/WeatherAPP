package com.firstapp.weatherapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TemperatureFragment extends Fragment {

    private static final String TAG = "TemperatureFragment";
    private TextView temperatureTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);
        temperatureTextView = view.findViewById(R.id.temperature_text_view);
        Log.d(TAG, "onCreateView: Fragment view created");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: Fragment view fully created");
    }

    public void updateTemperature(double temperature) {
        Log.d(TAG, "updateTemperature: Updating temperature to " + temperature);
        if (temperatureTextView != null) {
            String formattedTemperature = String.format("%.1f°C", temperature);
            requireActivity().runOnUiThread(() -> {
                temperatureTextView.setText(formattedTemperature);
                Log.d(TAG, "updateTemperature: Temperature updated in UI");
            });
        } else {
            Log.e(TAG, "updateTemperature: temperatureTextView is null");
        }
    }
}