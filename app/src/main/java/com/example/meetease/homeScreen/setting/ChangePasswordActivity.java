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

public class ChangePasswordActivity extends AppCompatActivity {

    EditText etvOldPassword, etvNewPassword, etvConfirmPassword;
    Button btnSave;
    TextView txtForgotPassword;
    ImageView ivBack;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

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

//                Toast.makeText(ChangePasswordActivity.this, "" + preferenceManager.getKeyValueString(VariableBag.password, ""), Toast.LENGTH_SHORT).show();
                if (!oldPass.equals(preferenceManager.getKeyValueString(VariableBag.password, ""))) {
                    Toast.makeText(ChangePasswordActivity.this, "Old Password is Wrong", Toast.LENGTH_SHORT).show();
                } else if (newPass.isEmpty()) {
                    etvNewPassword.setError("Password cannot be Empty");
                    etvNewPassword.requestFocus();
                } else if (!Tools.isValidPassword(newPass)) {
                    etvNewPassword.setError("Password Must Consist Of Minimum length of 7 with At-least 1 UpperCase, 1 LowerCase, 1 Number & 1 Special Character");
                    etvNewPassword.requestFocus();
                } else if (!confirmPass.equals(newPass)) {
                    etvConfirmPassword.setError("Confirm Password doesn't Match");
                    etvConfirmPassword.requestFocus();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Conditions Checked", Toast.LENGTH_SHORT).show();
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
}