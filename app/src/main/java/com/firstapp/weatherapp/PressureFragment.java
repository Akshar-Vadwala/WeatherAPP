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

public class PressureFragment extends Fragment {
    private static final String TAG = "PressureFragment";
    private static final double PA_TO_BAR = 1e-5;

    private TextView pressureTextView;
    private TextView altitudeTextView;
    private TextView pressureSeaTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pressure, container, false);

        pressureTextView = view.findViewById(R.id.pressure_text_view);
        altitudeTextView = view.findViewById(R.id.altitude_text_view);
        pressureSeaTextView = view.findViewById(R.id.pressure_sea_text_view);

        return view;
    }

    public void updatePressure(double pressure, double altitude, double pressureSea) {
        if (pressureTextView != null && altitudeTextView != null && pressureSeaTextView != null) {
            pressureTextView.setText(String.format("%.5f bar", pressure * PA_TO_BAR));
            altitudeTextView.setText(String.format("%.2f m", altitude));
            pressureSeaTextView.setText(String.format("%.5f bar", pressureSea * PA_TO_BAR));
        } else {
            Log.e(TAG, "updatePressure: One or more TextViews are null");
        }
    }
}