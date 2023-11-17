package com.example.meetease.homeScreen.createReservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.meetease.R;

public class DetailsActivity extends AppCompatActivity {

    ImageView imgRoom,imgFavourite;
    TextView txtName,txtLocation,txtPrice;
    RatingBar ratingBar;
    Button btnBookNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imgRoom = findViewById(R.id.imgRoom);
        imgFavourite = findViewById(R.id.imgFavourite);
        txtName = findViewById(R.id.txtName);
        txtLocation = findViewById(R.id.txtLocation);
        txtPrice = findViewById(R.id.txtPrice);
        ratingBar = findViewById(R.id.ratingBar);
        btnBookNow = findViewById(R.id.btnBookNow);


    }
}