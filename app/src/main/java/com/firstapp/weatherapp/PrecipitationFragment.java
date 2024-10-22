package com.firstapp.weatherapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PrecipitationFragment extends Fragment {

    private TextView precipitationTextView;
    private ImageView precipitationImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_precipitation, container, false);

        // Initialize views
        precipitationTextView = view.findViewById(R.id.precipitation_text_view);
        precipitationImageView = view.findViewById(R.id.precipitation_image_view);

        return view;
    }

    public void updatePrecipitation(int waterValue) {
        if (precipitationTextView != null && precipitationImageView != null) {
            String precipitationStatus = getPrecipitationStatus(waterValue);

            // Update text for precipitation
            precipitationTextView.post(() -> precipitationTextView.setText(precipitationStatus));

            // Update image based on precipitation value
            precipitationImageView.post(() -> precipitationImageView.setImageResource(getPrecipitationImage(waterValue)));
        }
    }

    // Determine the precipitation status based on waterValue
    private String getPrecipitationStatus(int waterValue) {
        if (waterValue < 300) {
            return "No Rain";
        } else if (waterValue < 500) {
            return "Light Rain";
        } else {
            return "Heavy Rain";
        }
    }

    // Get the appropriate image based on the precipitation value
    private int getPrecipitationImage(int waterValue) {
        if (waterValue < 300) {
            return R.drawable.no_rain;
        } else if (waterValue < 500) {
            return R.drawable.light_rain;
        } else {
            return R.drawable.heavy_rain;
        }
    }
}
