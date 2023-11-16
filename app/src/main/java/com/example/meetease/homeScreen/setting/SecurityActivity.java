package com.example.meetease.homeScreen.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.homeScreen.HomeScreenActivity;

import java.util.concurrent.Executor;

public class SecurityActivity extends AppCompatActivity {

   BiometricPrompt biometricPrompt;
   BiometricPrompt.PromptInfo promptInfo;
    Executor executor;
    PreferenceManager preferenceManager;
    SwitchCompat switchOnOff;
    ImageView ivBack;
    View accountStatus,mobileLock,notifyUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        accountStatus = findViewById(R.id.accountStatus);
        mobileLock = findViewById(R.id.mobileLock);
        notifyUpdate = findViewById(R.id.notifyUpdate);
        switchOnOff = findViewById(R.id.switchOnOff);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecurityActivity.this, HomeScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if (preferenceManager.getKeyValueBoolean(VariableBag.SecuritySwitchCheck)){
            switchOnOff.setChecked(true);
        }
        else {
            switchOnOff.setChecked(false);
        }
        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    preferenceManager.setKeyValueBoolean(VariableBag.SecuritySwitchCheck,true);
                }
                else {
                    preferenceManager.setKeyValueBoolean(VariableBag.SecuritySwitchCheck,false);
                }

            }
        });
        
        executor = ContextCompat.getMainExecutor(this);
        preferenceManager = new PreferenceManager(this);
        if (preferenceManager.getKeyValueBoolean(VariableBag.SecuritySwitchCheck)) {
            biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                }

                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Toast.makeText(SecurityActivity.this, errString, Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();

                    Toast.makeText(SecurityActivity.this, "FAILED !!", Toast.LENGTH_LONG).show();
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Touch id required")
                    .setDescription("Touch the touch id sensor")
                    .setNegativeButtonText("Exit")
                    .build();

            biometricPrompt.authenticate(promptInfo);
        }
    }
}

//<ImageView
//        android:id="@+id/ivBack"
//                android:layout_width="@dimen/_30sdp"
//                android:layout_height="@dimen/_30sdp"
//                android:src="@drawable/ic_back"
//                android:layout_margin="@dimen/_15sdp"/>