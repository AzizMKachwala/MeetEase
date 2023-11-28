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
    Boolean password = false;
    RestCall restCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etvName = findViewById(R.id.etvName);
        etvMobileNumber = findViewById(R.id.etvMobileNumber);
        etvPassword = findViewById(R.id.etvPassword);
        etvConfirmPassword = findViewById(R.id.etvConfirmPassword);
        etvEmail = findViewById(R.id.etvEmail);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtLogin = findViewById(R.id.txtLogin);
        imgPasswordCloseEye = findViewById(R.id.imgPasswordCloseEye);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        tools = new Tools(SignUpActivity.this);

        imgPasswordCloseEye.setOnClickListener(v -> {
            if (password.equals(false)) {
                password = true;
                etvPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                etvPassword.setSelection(etvPassword.length());
                imgPasswordCloseEye.setImageResource(R.drawable.ceye);
            } else {
                password = false;
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
        String name = etvName.getText().toString() , mobileNumber = etvMobileNumber.getText().toString() , email = etvEmail.getText().toString() , password = etvPassword.getText().toString(), confirmPassword = etvConfirmPassword.getText().toString();

        if (name.isEmpty()) {
            setError("Enter Name",etvName);
            return false;
        } else if (name.length() < 2) {
            setError("Enter Valid Name",etvName);
            return false;
        } else if (mobileNumber.isEmpty()) {
            setError("Enter Mobile Number",etvMobileNumber);
            return false;
        } else if (mobileNumber.length() != 10) {
            setError("Enter Mobile Number with 10 Digits",etvMobileNumber);
            return false;
        } else if (email.isEmpty()) {
            setError("Email Address cannot be Empty",etvEmail);
            return false;
        } else if (!Tools.isValidEmail(email)) {
            setError("Email Address must contain @ and .com in it",etvEmail);
            return false;
        } else if (password.isEmpty()) {
            setError("Password cannot be Empty",etvPassword);
            return false;
        } else if (!Tools.isValidPassword(password)) {
            setError("Password Must Consist Of Minimum length of 7 with At-least 1 UpperCase, 1 LowerCase, 1 Number & 1 Special Character",etvPassword);
            return false;
        } else if (confirmPassword.isEmpty()) {
            setError("Confirm Password cannot be Empty",etvConfirmPassword);
            return false;
        } else if (!password.equals(confirmPassword)) {
            setError("Confirm Password does not Match",etvConfirmPassword);
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
    void setError(String error,EditText etv){
        etv.setError(error);
        etv.requestFocus();
    }
}