package com.example.meetease.homeScreen.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.meetease.R;

public class RateUsActivity extends AppCompatActivity {

    RatingBar ratingBar;
    Button btnSubmit;
    TextView txtRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        ratingBar = findViewById(R.id.ratingBar);
        btnSubmit = findViewById(R.id.btnSubmit);
        txtRating = findViewById(R.id.txtRating);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (ratingBar.getRating() == 1) {
                    txtRating.setText("Not Good");
                } else if (ratingBar.getRating() == 2) {
                    txtRating.setText("Good");
                } else if (ratingBar.getRating() == 3) {
                    txtRating.setText("Average");
                } else if (ratingBar.getRating() == 4) {
                    txtRating.setText("excellent");
                } else {
                    txtRating.setText("I really Liked it");
                }
            }
        });
    }
}