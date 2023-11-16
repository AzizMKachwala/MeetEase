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


    PreferenceManager preferenceManager;
    SwitchCompat switchOnOff;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        preferenceManager = new PreferenceManager(this);
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
        

    }
}

//<ImageView
//        android:id="@+id/ivBack"
//                android:layout_width="@dimen/_30sdp"
//                android:layout_height="@dimen/_30sdp"
//                android:src="@drawable/ic_back"
//                android:layout_margin="@dimen/_15sdp"/>