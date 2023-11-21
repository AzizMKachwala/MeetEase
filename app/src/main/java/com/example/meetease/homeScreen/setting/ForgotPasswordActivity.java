package com.example.meetease.homeScreen.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.VariableBag;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etvPhoneNo, etvOTP, etvNewPassword, etvConfirmPassword;
    Button btnSend, btnSave;
    PreferenceManager preferenceManager;

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

        preferenceManager = new PreferenceManager(ForgotPasswordActivity.this);

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

                    etvNewPassword.setVisibility(View.VISIBLE);
                    etvConfirmPassword.setVisibility(View.VISIBLE);
                    btnSave.setVisibility(View.VISIBLE);
                }
            }
        });

        Toast.makeText(ForgotPasswordActivity.this, "" + preferenceManager.getKeyValueString(VariableBag.password, ""), Toast.LENGTH_SHORT).show();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etvNewPassword.getText().toString().isEmpty()
                        && etvConfirmPassword.getText().toString().equals(etvNewPassword.getText().toString())){

                    if (etvNewPassword.getText().toString().equals(preferenceManager.getKeyValueString(VariableBag.password, ""))) {
                        Toast.makeText(ForgotPasswordActivity.this, "New Password Cannot be Same as Old Password", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ForgotPasswordActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}