package com.example.meetease.homeScreen.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.example.meetease.homeScreen.HomeScreenActivity;
import com.example.meetease.homeScreen.ProfileActivity;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;
import com.example.meetease.network.UserResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etvPhoneNo, etvOTP, etvNewPassword, etvConfirmPassword;
    Button btnSend, btnSave, btnCheckOtp;
    ImageView ivBack;
    TextView tvCode;
    String verificationId;
    CountryCodePicker countryPicker;
    PreferenceManager preferenceManager;
    private FirebaseAuth mAuth;
    Tools tools;
    RestCall restCall;

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
        preferenceManager = new PreferenceManager(ForgotPasswordActivity.this);
        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        tools = new Tools(this);
        countryPicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                tvCode.setText("+ " + countryPicker.getSelectedCountryCode());
            }
        });

        mAuth = FirebaseAuth.getInstance();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPasswordActivity.this, ChangePasswordActivity.class));
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

                if (etvPhoneNo.getText().toString().isEmpty()) {
                    etvPhoneNo.setError("Enter Mobile Number");
                    etvPhoneNo.requestFocus();
                } else if (!etvPhoneNo.getText().toString().equals(preferenceManager.getKeyValueString(VariableBag.mobile, ""))) {
                    etvPhoneNo.setError("Use Valid Mobile Number !!!");
                    etvPhoneNo.requestFocus();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "OTP Sent...", Toast.LENGTH_SHORT).show();
                    String phone = "+" + countryPicker.getSelectedCountryCode() + etvPhoneNo.getText().toString();
                    tools.showLoading();
                    sendVerificationCode(phone);
                }
            }
        });

        btnCheckOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tools.showLoading();

                if (etvOTP.getText().toString().isEmpty()) {
                    etvOTP.setError("Enter OTP");
                    etvOTP.requestFocus();
                } else {
                    verifyCode(etvOTP.getText().toString());
                }

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etvNewPassword.getText().toString().isEmpty()
                        && etvConfirmPassword.getText().toString().equals(etvNewPassword.getText().toString())) {

                    if (etvNewPassword.getText().toString().equals(preferenceManager.getKeyValueString(VariableBag.password, ""))) {
                        Toast.makeText(ForgotPasswordActivity.this, "New Password Cannot be Same as Old Password", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "password change successfully", Toast.LENGTH_SHORT).show();
                        editPassword();
                    }
                }
            }
        });

    }

    private void sendVerificationCode(String phone) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
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
            tools.stopLoading();
            btnSend.setText("Resend");
            btnCheckOtp.setVisibility(View.VISIBLE);
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            tools.stopLoading();
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            tools.stopLoading();
            Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            btnCheckOtp.setVisibility(View.GONE);
                            btnSend.setVisibility(View.GONE);
                            etvPhoneNo.setEnabled(false);
                            etvOTP.setEnabled(false);
                            etvNewPassword.setVisibility(View.VISIBLE);
                            etvConfirmPassword.setVisibility(View.VISIBLE);
                            btnSave.setVisibility(View.VISIBLE);
                            tools.stopLoading();
                        } else {
                            etvOTP.setError("Enter Correct OTP");
                            etvOTP.requestFocus();
                        }
                    }
                });
    }

    void editPassword() {

        restCall.ResetPassword("UpdatePassword", preferenceManager.getKeyValueString(VariableBag.user_id, ""), etvNewPassword.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<UserResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ForgotPasswordActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ForgotPasswordActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                if (userResponse.getStatus().equals(VariableBag.SUCCESS_RESULT)) {
                                    preferenceManager.setKeyValueString(VariableBag.password, etvNewPassword.getText().toString());
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    finish();
                                }
                            }
                        });
                    }
                });
    }
}
