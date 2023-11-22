package com.example.meetease.homeScreen.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.VariableBag;

public class SecurityActivity extends AppCompatActivity {

    PreferenceManager preferenceManager;
    SwitchCompat switchOnOff;
    ImageView ivBack;
    ConstraintLayout lytChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        preferenceManager = new PreferenceManager(this);
        switchOnOff = findViewById(R.id.switchOnOff);
        ivBack = findViewById(R.id.ivBack);
        lytChangePassword = findViewById(R.id.lytChangePassword);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lytChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecurityActivity.this, ChangePasswordActivity.class));
                finish();
            }
        });

        switchOnOff.setChecked(preferenceManager.getKeyValueBoolean(VariableBag.SecuritySwitchCheck));

        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean status) {
                preferenceManager.setKeyValueBoolean(VariableBag.SecuritySwitchCheck, status);
            }
        });
    }
}