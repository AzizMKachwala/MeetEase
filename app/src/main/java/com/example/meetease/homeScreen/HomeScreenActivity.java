package com.example.meetease.homeScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.meetease.R;
import com.example.meetease.entryModule.LoginActivity;

public class HomeScreenActivity extends AppCompatActivity {

    View layoutAddReservation,layoutUpcomingMeeting,layoutPreviousMeeting,layoutUserProfile,layoutContactUs,layoutLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        layoutUserProfile = findViewById(R.id.layoutUserProfile);
        layoutAddReservation = findViewById(R.id.layoutAddReservation);
        layoutUpcomingMeeting = findViewById(R.id.layoutUpcomingMeeting);
        layoutPreviousMeeting = findViewById(R.id.layoutPreviousMeeting);
        layoutContactUs = findViewById(R.id.layoutContactUs);
        layoutLogout = findViewById(R.id.layoutLogout);

        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreenActivity.this);
                builder.setMessage("");
                builder.setTitle("");
                builder.setCancelable(false);
                builder.setPositiveButton("Back to Home", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                    Intent intent1 = new Intent(HomeScreenActivity.this, LoginActivity.class);
                    startActivity(intent1);
                    finish();
                });
                builder.setNegativeButton("Play Again", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}