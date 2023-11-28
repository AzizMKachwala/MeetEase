package com.example.meetease.homeScreen.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.homeScreen.ProfileActivity;

public class ProfileShowActivity extends AppCompatActivity {
    TextView txtName, txtEmail, txtMobile, txtRegisterDate, txtVersion;
    ImageView imgEdit, ivBack;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_show);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtMobile = findViewById(R.id.txtMobile);
        txtRegisterDate = findViewById(R.id.txtRegisterDate);
        txtVersion = findViewById(R.id.txtVersion);
        imgEdit = findViewById(R.id.imgEdit);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        preferenceManager = new PreferenceManager(ProfileShowActivity.this);

        txtName.setSelected(true);
        txtEmail.setSelected(true);
        txtMobile.setSelected(true);

        txtName.setText(preferenceManager.getKeyValueString(VariableBag.full_name, ""));
        txtEmail.setText(preferenceManager.getKeyValueString(VariableBag.email, ""));
        txtMobile.setText(preferenceManager.getKeyValueString(VariableBag.mobile, ""));
        txtVersion.setText(VariableBag.appVersion);
        txtRegisterDate.setText("");

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileShowActivity.this, ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }
}