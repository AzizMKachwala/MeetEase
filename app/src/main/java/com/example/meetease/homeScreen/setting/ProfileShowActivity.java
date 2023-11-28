package com.example.meetease.homeScreen.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meetease.R;
import com.example.meetease.homeScreen.ProfileActivity;

public class ProfileShowActivity extends AppCompatActivity {
    TextView txtName, txtEmail, txtMobile;
    ImageView imgEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_show);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtMobile = findViewById(R.id.txtMobile);
        imgEdit = findViewById(R.id.imgEdit);

        txtEmail.setSelected(true);

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