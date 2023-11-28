package com.example.meetease.entryModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.homeScreen.HomeScreenActivity;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;
import com.example.meetease.network.UserResponse;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity {

    EditText etvName, etvMobileNumber, etvEmail, etvPassword, etvConfirmPassword;
    Button btnSignUp;
    TextView txtLogin;
    ImageView imgPasswordCloseEye;
    Tools tools;
    String password = "Hide";
    RestCall restCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        etvName = findViewById(R.id.etvName);
        etvMobileNumber = findViewById(R.id.etvMobileNumber);
        etvPassword = findViewById(R.id.etvPassword);
        etvConfirmPassword = findViewById(R.id.etvConfirmPassword);
        etvEmail = findViewById(R.id.etvEmail);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtLogin = findViewById(R.id.txtLogin);
        imgPasswordCloseEye = findViewById(R.id.imgPasswordCloseEye);

        tools = new Tools(SignUpActivity.this);

        imgPasswordCloseEye.setOnClickListener(v -> {

            if (password.equals("Hide")) {
                password = "Show";
                etvPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                etvPassword.setSelection(etvPassword.length());
                imgPasswordCloseEye.setImageResource(R.drawable.ceye);
            } else {
                password = "Hide";
                etvPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                etvPassword.setSelection(etvPassword.length());
                imgPasswordCloseEye.setImageResource(R.drawable.baseline_eye_24);
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation(etvName, etvMobileNumber, etvEmail, etvPassword, etvConfirmPassword);
                if (validation(etvName, etvMobileNumber, etvEmail, etvPassword, etvConfirmPassword)) {
                    tools.showLoading();
                    AddUser();
                }
            }
        });
    }

    Boolean validation(EditText etvName, EditText etvMobileNumber, EditText etvEmail, EditText etvPassword, EditText etvConfirmPassword) {
        String name = etvName.getText().toString();
        String mobileNumber = etvMobileNumber.getText().toString();
        String email = etvEmail.getText().toString();
        String password = etvPassword.getText().toString();
        String confirmPassword = etvConfirmPassword.getText().toString();

        if (name.isEmpty()) {
            etvName.setError("Enter Name");
            etvName.requestFocus();
            return false;
        } else if (name.length() < 2) {
            etvName.setError("Enter Valid Name");
            etvName.requestFocus();
            return false;
        } else if (mobileNumber.isEmpty()) {
            etvMobileNumber.setError("Enter Mobile Number");
            etvMobileNumber.requestFocus();
            return false;
        } else if (mobileNumber.length() != 10) {
            etvMobileNumber.setError("Enter Mobile Number with 10 Digits");
            etvMobileNumber.requestFocus();
            return false;
        } else if (email.isEmpty()) {
            etvEmail.setError("Email Address cannot be Empty");
            etvEmail.requestFocus();
            return false;
        } else if (!Tools.isValidEmail(email)) {
            etvEmail.setError("Email Address must contain @ and .com in it");
            etvEmail.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            etvPassword.setError("Password cannot be Empty");
            etvPassword.requestFocus();
            return false;
        } else if (!Tools.isValidPassword(password)) {
            etvPassword.setError("Password Must Consist Of Minimum length of 7 with At-least 1 UpperCase, 1 LowerCase, 1 Number & 1 Special Character");
            etvPassword.requestFocus();
            return false;
        } else if (confirmPassword.isEmpty()) {
            etvConfirmPassword.setError("Confirm Password cannot be Empty");
            etvConfirmPassword.requestFocus();
            return false;
        } else if (!password.equals(confirmPassword)) {
            etvConfirmPassword.setError("Confirm Password does not Match");
            etvConfirmPassword.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    void AddUser(){
        restCall.AddUser("AddUser",etvName.getText().toString(),etvEmail.getText().toString(),etvMobileNumber.getText().toString(),etvPassword.getText().toString())
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
                                tools.stopLoading();
                                Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Toast.makeText(SignUpActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                if(userResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)){
                                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    finish();
                                }
                            }
                        });
                    }
                });

    }
}