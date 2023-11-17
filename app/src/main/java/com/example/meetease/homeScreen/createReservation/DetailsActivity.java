package com.example.meetease.homeScreen.createReservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meetease.R;

public class DetailsActivity extends AppCompatActivity {

    ImageView imgRoom,imgFavourite;
    TextView txtName,txtLocation,txtPrice;
    RatingBar ratingBar;
    Button btnBookNow;
    String checkFavourite;
    String roomName,roomPrice,roomLocation,roomRating,roomImage;

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

        Intent intent = getIntent();
        roomName = intent.getStringExtra("roomName");
        roomPrice = intent.getStringExtra("roomPrice");
        roomLocation = intent.getStringExtra("roomLocation");
        roomRating = intent.getStringExtra("roomRating");
        roomImage = intent.getStringExtra("roomImage");

        txtLocation.setText(roomLocation);
        txtName.setText(roomName);
        txtPrice.setText(roomPrice+" "+"$"+"/Hour");
        ratingBar.setRating(Float.parseFloat(roomRating));

        Glide
                .with(this)
                .load(roomImage)
                .into(imgRoom);

        checkFavourite = "0";
        if (checkFavourite.equals("0")){
            imgFavourite.setImageResource(R.drawable.baseline_star_outline_24);
        }
        else {
            imgFavourite.setImageResource(R.drawable.baseline_star_filled_24);
        }
        imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFavourite.equals("0")){
                    imgFavourite.setImageResource(R.drawable.baseline_star_outline_24);
                    checkFavourite = "1";
                }
                else {
                    imgFavourite.setImageResource(R.drawable.baseline_star_filled_24);
                    checkFavourite = "0";
                }
            }
        });
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this,BookMeetingActivity.class);
                intent.putExtra("roomName",roomName);
                intent.putExtra("roomPrice",roomPrice);
                intent.putExtra("roomLocation",roomLocation);
                startActivity(intent);
                finish();
            }
        });

    }
}