package com.firstapp.weatherapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HumidityFragment extends Fragment {

    private TextView humidityTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_humidity, container, false);

        humidityTextView = view.findViewById(R.id.humidity_text_view);

        return view;
    }

    public void updateHumidity(double humidity) {
        if (humidityTextView != null) {
            String formattedHumidity = String.format("%.1f%%", humidity);
            humidityTextView.post(() -> humidityTextView.setText(formattedHumidity));
        }
    }
}