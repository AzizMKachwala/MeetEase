package com.example.meetease.entryModule;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetease.R;

public class GuideActivity extends AppCompatActivity {

    Button btnNext, btnSkip;
    ImageView imgSteps;
    TextView txtSteps;
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        btnNext = findViewById(R.id.btnNext);
        imgSteps = findViewById(R.id.imgSteps);
        txtSteps = findViewById(R.id.txtSteps);
        btnSkip = findViewById(R.id.btnSkip);

        txtSteps.setText("Step 1");
        imgSteps.setImageResource(R.drawable.logo);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos == 0) {
                    txtSteps.setText("Step 2");
                    imgSteps.setImageResource(R.drawable.ceye);
                    pos++;
                } else if (pos == 1) {
                    txtSteps.setText("Step 3");
                    imgSteps.setImageResource(R.drawable.baseline_eye_24);
                    pos++;
                } else if (pos == 2) {
                    txtSteps.setText("Step 4");
                    imgSteps.setImageResource(R.drawable.baseline_photo_camera_24);
                    btnNext.setText("Finish");
                    pos++;
                }
                else {
                    finish();
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}