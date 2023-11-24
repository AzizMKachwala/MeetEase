package com.example.meetease.homeScreen.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;
import com.example.meetease.network.UserResponse;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText etvOldPassword, etvNewPassword, etvConfirmPassword;
    Button btnSave;
    TextView txtForgotPassword;
    ImageView ivBack;
    PreferenceManager preferenceManager;
    RestCall restCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        etvOldPassword = findViewById(R.id.etvOldPassword);
        etvNewPassword = findViewById(R.id.etvNewPassword);
        etvConfirmPassword = findViewById(R.id.etvConfirmPassword);
        btnSave = findViewById(R.id.btnSave);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        preferenceManager = new PreferenceManager(ChangePasswordActivity.this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oldPass = etvOldPassword.getText().toString();
                String newPass = etvNewPassword.getText().toString();
                String confirmPass = etvConfirmPassword.getText().toString();

                if (!oldPass.equals(preferenceManager.getKeyValueString(VariableBag.password, ""))) {
                    Toast.makeText(ChangePasswordActivity.this, "Old Password is Wrong", Toast.LENGTH_SHORT).show();
                } else if (newPass.isEmpty()) {
                    etvNewPassword.setError("Password cannot be Empty");
                    etvNewPassword.requestFocus();
                } else if (etvNewPassword.getText().toString().equals(preferenceManager.getKeyValueString(VariableBag.password, ""))) {
                    etvNewPassword.setError("New Password Cannot be Same as Old Password");
                    etvNewPassword.requestFocus();
                } else if (!Tools.isValidPassword(newPass)) {
                    etvNewPassword.setError("Password Must Consist Of Minimum length of 7 with At-least 1 UpperCase, 1 LowerCase, 1 Number & 1 Special Character");
                    etvNewPassword.requestFocus();
                } else if (!confirmPass.equals(newPass)) {
                    etvConfirmPassword.setError("Confirm Password doesn't Match");
                    etvConfirmPassword.requestFocus();
                } else {
                    editPassword();
                }
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePasswordActivity.this, ForgotPasswordActivity.class));
                finish();
            }
        });
    }
    void editPassword() {
        restCall.ResetPassword("UpdatePassword",preferenceManager.getKeyValueString(VariableBag.user_id,""),etvNewPassword.getText().toString())
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
                                Toast.makeText(ChangePasswordActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChangePasswordActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                if (userResponse.getStatus().equals(VariableBag.SUCCESS_RESULT)){
                                    preferenceManager.setKeyValueString(VariableBag.password,etvNewPassword.getText().toString());
                                    finish();
                                }
                            }
                        });
                    }
                });
    }
}