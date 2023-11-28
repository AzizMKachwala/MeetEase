package com.example.meetease.homeScreen;

import android.annotation.SuppressLint;
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

import com.example.meetease.homeScreen.previousMeeting.PreviousMeetingActivity;
import com.example.meetease.homeScreen.setting.AvailableRoomsActivity;
import com.example.meetease.homeScreen.setting.ContactUsActivity;
import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.entryModule.GuideActivity;
import com.example.meetease.entryModule.LoginActivity;
import com.example.meetease.homeScreen.createReservation.BookMeetingActivity;
import com.example.meetease.homeScreen.setting.FaqActivity;
import com.example.meetease.homeScreen.setting.ProfileShowActivity;
import com.example.meetease.homeScreen.setting.RateUsActivity;
import com.example.meetease.homeScreen.setting.SecurityActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.concurrent.Executor;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {

    View scrollView, favoriteRooms, availableRooms, security, howToBookRoom,
            inviteFriend, helpAndSupport, logout, layoutAddReservation, layoutUpcomingMeeting,
            layoutPreviousMeeting, layoutUserProfile, layoutContactUs, layoutRateUs;
    ImageView ivSettingProfile, ivSetting;
    TextView tvSettingName, tvSettingEmail, tvTrans, txtHelloName;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    Executor executor;
    PreferenceManager preferenceManager;
    FirebaseAuth auth;

    @SuppressLint("SetTextI18n")
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
        layoutRateUs = findViewById(R.id.layoutRateUs);
        favoriteRooms = findViewById(R.id.favoriteRooms);
        availableRooms = findViewById(R.id.availableRooms);
        security = findViewById(R.id.security);
        tvTrans = findViewById(R.id.tvTrans);
        txtHelloName = findViewById(R.id.txtHelloName);
        scrollView = findViewById(R.id.scrollView);
        howToBookRoom = findViewById(R.id.howToBookRoom);
        inviteFriend = findViewById(R.id.inviteFriend);
        helpAndSupport = findViewById(R.id.helpAndSupport);
        logout = findViewById(R.id.logout);
        ivSetting = findViewById(R.id.ivSetting);
        ivSettingProfile = findViewById(R.id.ivSettingProfile);
        tvSettingName = findViewById(R.id.tvSettingName);
        tvSettingEmail = findViewById(R.id.tvSettingEmail);

        scrollView.setVisibility(View.GONE);
        tvTrans.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();

        ivSetting.setOnClickListener(this);
        logout.setOnClickListener(this);
        howToBookRoom.setOnClickListener(this);
        layoutContactUs.setOnClickListener(this);
        helpAndSupport.setOnClickListener(this);
        layoutUserProfile.setOnClickListener(this);
        security.setOnClickListener(this);
        availableRooms.setOnClickListener(this);
        layoutAddReservation.setOnClickListener(this);
        layoutUpcomingMeeting.setOnClickListener(this);
        layoutRateUs.setOnClickListener(this);
        tvTrans.setOnClickListener(this);
        inviteFriend.setOnClickListener(this);
        layoutPreviousMeeting.setOnClickListener(this);
        favoriteRooms.setOnClickListener(this);

        biometric();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        tvSettingName.setText(preferenceManager.getKeyValueString(VariableBag.full_name, ""));
        tvSettingEmail.setText(preferenceManager.getKeyValueString(VariableBag.email, ""));
        txtHelloName.setText("Hello, " + preferenceManager.getKeyValueString(VariableBag.full_name, ""));
    }

    @Override
    public void onBackPressed() {
        if (scrollView.getVisibility() == View.VISIBLE) {
            scrollView.setVisibility(View.GONE);
            tvTrans.setVisibility(View.GONE);
        } else {
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
        if (view == favoriteRooms) {
            Intent intent = new Intent(HomeScreenActivity.this, PreviousMeetingActivity.class);
            intent.putExtra("abc", "favoriteRooms");
            startActivity(intent);
        }
        if (view == layoutPreviousMeeting) {
            changeScreen(PreviousMeetingActivity.class);
        }
        if (view == security) {
            changeScreen(SecurityActivity.class);
        }

        if (view == tvTrans) {
            scrollView.setVisibility(View.GONE);
            tvTrans.setVisibility(View.GONE);
        }

        if (view == layoutUpcomingMeeting) {
//            changeScreen();
        }

        if (view == inviteFriend) {
//            generateInvitationLink();
        }

        if (view == availableRooms) {
            changeScreen(AvailableRoomsActivity.class);
        }

        if (view == layoutAddReservation) {
            changeScreen(BookMeetingActivity.class);
        }

        if (view == howToBookRoom) {
            changeScreen(GuideActivity.class);
        }

        if (view == layoutUserProfile) {
            changeScreen(ProfileShowActivity.class);
        }

        if (view == layoutContactUs) {
            changeScreen(ContactUsActivity.class);
        }

        if (view == helpAndSupport) {
            changeScreen(FaqActivity.class);
        }

        if (view == layoutRateUs) {
            changeScreen(RateUsActivity.class);
        }

        if (view == logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreenActivity.this);
            builder.setMessage("Are You Sure You Want To Logout?");
            builder.setTitle("Alert !!");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    for (UserInfo userInfo : currentUser.getProviderData()) {
                        if ("google.com".equals(userInfo.getProviderId())) {
                            // User signed in with Google
                            auth.signOut();
                        } else {
                            // Assume other providers as simple email/password
                            preferenceManager.setKeyValueBoolean(VariableBag.SessionManage, false);
                        }
                    }
                }
                changeScreen(LoginActivity.class);
                finish();
            });
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

//    private void generateInvitationLink() {
//        String invitationLink = "https://example.com/invite?userId=" + preferenceManager.getKeyValueString(VariableBag.user_id,"");
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, "Join MeetEase using my invitation link: " + invitationLink);
//        sendIntent.setType("text/plain");
//
//        Intent shareIntent = Intent.createChooser(sendIntent, null);
//        startActivity(shareIntent);
//    }

    void changeScreen(Class classActivity) {
        Intent intent = new Intent(HomeScreenActivity.this, classActivity);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                    Toast.makeText(HomeScreenActivity.this, "FAILED !!!", Toast.LENGTH_LONG).show();
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