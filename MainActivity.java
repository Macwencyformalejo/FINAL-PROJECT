package com.example.mazeblock;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    // Declare UI components
    private TextInputEditText etWeight, etHeight;
    private MaterialButton btnCalculate;
    private TextView tvMazedValue, tvMazedStatus, tvMessage, tvCategory;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components (connecting Java code to XML layout)
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnCalculate = findViewById(R.id.btnCalculate);

        tvMazedValue = findViewById(R.id.tvMazedValue);
        tvMazedStatus = findViewById(R.id.tvMazedStatus);
        tvMessage = findViewById(R.id.tvMessage);
        tvCategory = findViewById(R.id.tvCategory);
        progressBar = findViewById(R.id.progressBar);

        // Set click listener for Calculate button
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateMazedPower();
            }
        });
    }

    // Method to calculate Mazed Power
    private void calculateMazedPower() {
        // Get text from input fields
        String weightStr = etWeight.getText().toString();
        String heightStr = etHeight.getText().toString();

        // Check if fields are empty
        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            Toast.makeText(this, "Enter block weight and tower height", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Convert strings to numbers
            float blockWeight = Float.parseFloat(weightStr); // block weight
            float towerHeight = Float.parseFloat(heightStr); // tower height (cm)

            // Validate inputs
            if (blockWeight <= 0 || towerHeight <= 0) {
                Toast.makeText(this, "Enter valid positive values", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert height to meters (for game physics)
            float heightM = towerHeight / 100;

            // Calculate Mazed Power Score
            float mazedPower = blockWeight / (heightM * heightM);

            // Display Mazed value (rounded)
            tvMazedValue.setText(String.format("%.1f", mazedPower));

            // Update UI status
            updateMazedStatus(mazedPower);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Only numbers allowed", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to update Mazed Power Status
    private void updateMazedStatus(float power) {

        String status;
        String message;
        int progress;

        if (power < 15.9) {
            status = "Weak Block";
            message = "Your block is too fragile. It breaks easily in Mazed!";
            tvMazedStatus.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
            tvCategory.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
            progress = 10;

        } else if (power < 17.0) {
            status = "Light Block";
            message = "Your block needs reinforcement!";
            tvMazedStatus.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
            tvCategory.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
            progress = 25;

        } else if (power < 18.5) {
            status = "Standard Block";
            message = "Your block is stable but could be stronger.";
            tvMazedStatus.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
            tvCategory.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
            progress = 40;

        } else if (power < 25.0) {
            status = "Strong Block";
            message = "Great! Your block can handle most Mazed challenges.";
            tvMazedStatus.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            tvCategory.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            progress = 60;

        } else if (power < 30.0) {
            status = "Heavy Block";
            message = "Good power! But your block may slow movement.";
            tvMazedStatus.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            tvCategory.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            progress = 75;

        } else {
            status = "Mega Block!";
            message = "Amazing! Your block is unstoppable in Mazed!";
            tvMazedStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            tvCategory.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            progress = 90;
        }

        // Update UI
        tvMazedStatus.setText(status);
        tvCategory.setText(status);
        tvMessage.setText(message);
        progressBar.setProgress(progress);
    }
}
