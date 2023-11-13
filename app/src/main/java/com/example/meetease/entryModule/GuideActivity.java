package com.example.meetease.entryModule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetease.R;

public class GuideActivity extends AppCompatActivity {

    Button btnNext;
    ImageView imgSteps;
    TextView txtSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        btnNext = findViewById(R.id.btnNext);
        imgSteps = findViewById(R.id.imgSteps);
        txtSteps = findViewById(R.id.txtSteps);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}