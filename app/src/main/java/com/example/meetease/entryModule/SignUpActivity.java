package com.example.meetease.entryModule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetease.R;

public class SignUpActivity extends AppCompatActivity {

    EditText etvName,etvMobileNumber,etvEmail,etvPassword,etvConfirmPassword;
    Button btnLogin;
    TextView txtSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etvName = findViewById(R.id.etvName);
        etvMobileNumber = findViewById(R.id.etvMobileNumber);
        etvPassword = findViewById(R.id.etvPassword);
        etvConfirmPassword = findViewById(R.id.etvConfirmPassword);
        etvEmail = findViewById(R.id.etvEmail);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUp = findViewById(R.id.txtSingUp);
    }
    Boolean validation(){



        return true;
    }
}