package com.example.meetease.activity.homeScreen.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.meetease.R;

public class SampleActivity extends AppCompatActivity {

    TextView txtSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        txtSample = findViewById(R.id.txtSample);

        txtSample.setText("Coming Soon...");
    }
}