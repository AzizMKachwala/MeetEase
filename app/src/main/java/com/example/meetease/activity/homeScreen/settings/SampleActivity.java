package com.example.meetease.activity.homeScreen.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetease.R;

public class SampleActivity extends AppCompatActivity {

    TextView txtSample;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        txtSample = findViewById(R.id.txtSample);
        ivBack = findViewById(R.id.ivBack);

        txtSample.setText("Coming Soon...");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}