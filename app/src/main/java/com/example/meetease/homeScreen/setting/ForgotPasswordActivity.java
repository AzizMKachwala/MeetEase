package com.example.meetease.homeScreen.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meetease.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etvPhoneNo, etvOTP, etvNewPassword, etvConfirmPassword;
    Button btnSend, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etvPhoneNo = findViewById(R.id.etvPhoneNo);
        etvOTP = findViewById(R.id.etvOTP);
        etvNewPassword = findViewById(R.id.etvNewPassword);
        etvConfirmPassword = findViewById(R.id.etvConfirmPassword);
        btnSend = findViewById(R.id.btnSend);
        btnSave = findViewById(R.id.btnSave);

        btnSend.setText("Send");
        etvOTP.setVisibility(View.GONE);
        etvNewPassword.setVisibility(View.GONE);
        etvConfirmPassword.setVisibility(View.GONE);
        btnSave.setVisibility(View.GONE);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etvPhoneNo.getText().toString().isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "OTP Sent...", Toast.LENGTH_SHORT).show();
                    etvOTP.setVisibility(View.VISIBLE);
                    btnSend.setText("Resend");
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ForgotPasswordActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}