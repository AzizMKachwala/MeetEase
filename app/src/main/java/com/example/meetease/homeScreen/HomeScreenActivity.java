package com.example.meetease.homeScreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meetease.R;
import com.example.meetease.entryModule.LoginActivity;
import com.example.meetease.homeScreen.createReservation.CreateReservationActivity;
import com.example.meetease.homeScreen.setting.SecurityActivity;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {

    View scrollView, favoriteRooms, availableRooms, security, howToBookRoom, inviteFriend, helpAndSupport, logout,layoutAddReservation, layoutUpcomingMeeting, layoutPreviousMeeting, layoutUserProfile, layoutContactUs, layoutLogout;
    ImageView ivSettingProfile, ivSetting;
    TextView tvSettingName, tvTrans;

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
        favoriteRooms = findViewById(R.id.favoriteRooms);
        availableRooms = findViewById(R.id.availableRooms);
        security = findViewById(R.id.security);
        tvTrans = findViewById(R.id.tvTrans);
        scrollView = findViewById(R.id.scrollView);
        howToBookRoom = findViewById(R.id.howToBookRoom);
        inviteFriend = findViewById(R.id.inviteFriend);
        helpAndSupport = findViewById(R.id.helpAndSupport);
        logout = findViewById(R.id.logout);
        ivSetting = findViewById(R.id.ivSetting);
        ivSettingProfile = findViewById(R.id.ivSettingProfile);
        tvSettingName = findViewById(R.id.tvSettingName);


        scrollView.setVisibility(View.GONE);
        tvTrans.setVisibility(View.GONE);
        ivSetting.setOnClickListener(this);
        logout.setOnClickListener(this);
        availableRooms.setOnClickListener(this);
        layoutAddReservation.setOnClickListener(this);
        layoutLogout.setOnClickListener(this);
        tvTrans.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == ivSetting) {
            scrollView.setVisibility(View.VISIBLE);
            tvTrans.setVisibility(View.VISIBLE);
        }
        if (view == security) {
            Intent intent = new Intent(HomeScreenActivity.this, SecurityActivity.class);
            startActivity(intent);
            finish();
        }

        if (view == tvTrans) {
            scrollView.setVisibility(View.GONE);
            tvTrans.setVisibility(View.GONE);
        }
        if (view == availableRooms || view == layoutAddReservation) {
            Intent intent = new Intent(HomeScreenActivity.this, CreateReservationActivity.class);
            startActivity(intent);
            finish();
        }
        if (view == layoutLogout || view == logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreenActivity.this);
            builder.setMessage("Are You Sure You Want To Logout?");
            builder.setTitle("Alert !!");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
                Intent intent1 = new Intent(HomeScreenActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
            });
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}