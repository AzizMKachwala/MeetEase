package com.example.meetease.homeScreen.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etvPhoneNo, etvOTP, etvNewPassword, etvConfirmPassword;
    Button btnSend, btnSave, btnCheckOtp;
    ImageView ivBack;
    TextView tvCode;
    String verificationId,code;
    CountryCodePicker countryPicker;
    PreferenceManager preferenceManager;
    private FirebaseAuth mAuth;
    Tools tools;

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
        btnCheckOtp = findViewById(R.id.btnCheckOtp);
        ivBack = findViewById(R.id.ivBack);
        tvCode = findViewById(R.id.tvCode);
        countryPicker = findViewById(R.id.countryPicker);

        countryPicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                tvCode.setText("+ " + countryPicker.getSelectedCountryCode());
            }
        });

        mAuth = FirebaseAuth.getInstance();
        tools = new Tools(this);
        preferenceManager = new PreferenceManager(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSend.setText("Send");
        etvOTP.setVisibility(View.GONE);
        etvNewPassword.setVisibility(View.GONE);
        etvConfirmPassword.setVisibility(View.GONE);
        btnSave.setVisibility(View.GONE);
        btnCheckOtp.setVisibility(View.GONE);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etvPhoneNo.getText().toString().isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "OTP Sent...", Toast.LENGTH_SHORT).show();

                    String phone = "+" + countryPicker.getSelectedCountryCode() + etvPhoneNo.getText().toString();
                    sendVerificationCode(phone);
                }
            }
        });

        btnCheckOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCheckOtp.setVisibility(View.GONE);
                btnSend.setVisibility(View.GONE);
                etvPhoneNo.setEnabled(false);
                etvOTP.setEnabled(false);
                etvNewPassword.setVisibility(View.VISIBLE);
                etvConfirmPassword.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
            }
        });

//        Toast.makeText(ForgotPasswordActivity.this, "" + preferenceManager.getKeyValueString(VariableBag.password, ""), Toast.LENGTH_SHORT).show();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etvNewPassword.getText().toString().isEmpty()
                        && etvConfirmPassword.getText().toString().equals(etvNewPassword.getText().toString())) {

                    if (etvNewPassword.getText().toString().equals(preferenceManager.getKeyValueString(VariableBag.password, ""))) {
                        Toast.makeText(ForgotPasswordActivity.this, "New Password Cannot be Same as Old Password", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                    }
                }
//                else {
//                    if(etvNewPassword.getText().toString().isEmpty()){
//                        Toast.makeText(ForgotPasswordActivity.this, "New Password cannot be Empty", Toast.LENGTH_SHORT).show();
//                    } else if (etvConfirmPassword.getText().toString().equals(etvNewPassword.getText().toString())) {
//                        Toast.makeText(ForgotPasswordActivity.this, "Confirm Password doesn't match", Toast.LENGTH_SHORT).show();
//                    }
//                }
            }
        });

    }

    private void sendVerificationCode(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            etvOTP.setVisibility(View.VISIBLE);
            btnSend.setText("Resend");
            btnCheckOtp.setVisibility(View.VISIBLE);
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            code = phoneAuthCredential.getSmsCode();
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    };
}
