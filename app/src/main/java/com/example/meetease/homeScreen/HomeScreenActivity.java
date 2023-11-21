package com.example.meetease.homeScreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.meetease.homeScreen.setting.AvailableRoomsActivity;
import com.example.meetease.homeScreen.setting.ContactUsActivity;
import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.entryModule.GuideActivity;
import com.example.meetease.entryModule.LoginActivity;
import com.example.meetease.homeScreen.createReservation.BookMeetingActivity;
import com.example.meetease.homeScreen.setting.SecurityActivity;

import java.util.concurrent.Executor;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {

    View scrollView, favoriteRooms, availableRooms, security, howToBookRoom,
            inviteFriend, helpAndSupport, logout, layoutAddReservation, layoutUpcomingMeeting,
            layoutPreviousMeeting, layoutUserProfile, layoutContactUs, layoutLogout;
    ImageView ivSettingProfile, ivSetting;
    TextView tvSettingName, tvTrans;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    Executor executor;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        preferenceManager = new PreferenceManager(this);
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
        howToBookRoom.setOnClickListener(this);
        layoutContactUs.setOnClickListener(this);
        helpAndSupport.setOnClickListener(this);
        layoutUserProfile.setOnClickListener(this);
        security.setOnClickListener(this);
        availableRooms.setOnClickListener(this);
        layoutAddReservation.setOnClickListener(this);
        layoutLogout.setOnClickListener(this);
        tvTrans.setOnClickListener(this);

        tvSettingName.setText(preferenceManager.getKeyValueString(VariableBag.full_name,""));
        biometric();
    }

    @Override
    public void onBackPressed() {
        if(scrollView.getVisibility() == View.VISIBLE){
            scrollView.setVisibility(View.GONE);
            tvTrans.setVisibility(View.GONE);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreenActivity.this);
            builder.setMessage("Are You Sure You Want To Exit?");
            builder.setTitle("Alert !!");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
                finish();
            });
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    @Override
    public void onClick(View view) {
        if (view == ivSetting) {
            scrollView.setVisibility(View.VISIBLE);
            tvTrans.setVisibility(View.VISIBLE);

            Animation slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in);
            scrollView.startAnimation(slideInAnimation);
            ivSetting.startAnimation(slideInAnimation);
            tvTrans.startAnimation(slideInAnimation);
        }

        if (view == security) {
            Intent intent = new Intent(HomeScreenActivity.this, SecurityActivity.class);
            startActivity(intent);
        }

        if (view == tvTrans) {
            scrollView.setVisibility(View.GONE);
            tvTrans.setVisibility(View.GONE);
        }

        if (view == availableRooms ) {
            Intent intent = new Intent(HomeScreenActivity.this, AvailableRoomsActivity.class);
            startActivity(intent);
        }

        if(view == layoutAddReservation){
            Intent intent = new Intent(HomeScreenActivity.this, BookMeetingActivity.class);
            startActivity(intent);
        }

        if (view == howToBookRoom) {
            Intent intent = new Intent(HomeScreenActivity.this, GuideActivity.class);
            startActivity(intent);
        }

        if (view == layoutUserProfile) {
            Intent intent = new Intent(HomeScreenActivity.this, ProfileActivity.class);
            startActivity(intent);
        }

        if (view == layoutContactUs) {
            Intent intent = new Intent(HomeScreenActivity.this, ContactUsActivity.class);
            startActivity(intent);
        }

        if (view == helpAndSupport) {
            Intent intent = new Intent(HomeScreenActivity.this, ContactUsActivity.class);
            startActivity(intent);
        }

        if (view == layoutLogout || view == logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreenActivity.this);
            builder.setMessage("Are You Sure You Want To Logout?");
            builder.setTitle("Alert !!");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
                preferenceManager.setKeyValueBoolean(VariableBag.SessionManage, false);
                Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            });
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }


    void biometric() {
        executor = ContextCompat.getMainExecutor(this);
        if (preferenceManager.getKeyValueBoolean(VariableBag.SecuritySwitchCheck)) {
            biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                }

                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Toast.makeText(HomeScreenActivity.this, errString, Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Toast.makeText(HomeScreenActivity.this, "FAILED !!", Toast.LENGTH_LONG).show();
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Unlock MeetEase")
                    .setDescription("Use Password,Pattern or Fingerprint to Unlock.")
                    .setDeviceCredentialAllowed(true)
                    .setNegativeButtonText(null)
                    .build();

            biometricPrompt.authenticate(promptInfo);
        }
    }
}