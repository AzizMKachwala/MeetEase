package com.example.meetease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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