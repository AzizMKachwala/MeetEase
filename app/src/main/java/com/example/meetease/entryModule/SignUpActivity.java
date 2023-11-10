package com.example.meetease.entryModule;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.meetease.R;
import com.example.meetease.tools.Tools;

public class SignUpActivity extends AppCompatActivity {

    EditText etvName,etvMobileNumber,etvEmail,etvPassword,etvConfirmPassword;
    Button btnSignUp;
    TextView txtLogin;
    Tools tools;
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
        tools = new Tools(SignUpActivity.this);


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation(etvName,etvMobileNumber,etvEmail,etvPassword,etvConfirmPassword);
                if (validation(etvName,etvMobileNumber,etvEmail,etvPassword,etvConfirmPassword)){

                }
            }
        });
    }
    Boolean validation(EditText etvName,EditText etvMobileNumber,EditText etvEmail,EditText etvPassword,EditText etvConfirmPassword){
        String name = etvName.getText().toString();
        String mobileNumber = etvMobileNumber.getText().toString();
        String email = etvEmail.getText().toString();
        String password = etvPassword.getText().toString();
        String confirmPassword = etvConfirmPassword.getText().toString();

        if (name.isEmpty()){
            etvName.setError("enter your name");
            etvName.requestFocus();
        }
        else if (name.length() < 2){
            etvName.setError("enter valid Name");
            etvName.requestFocus();
        }
        else if (mobileNumber.isEmpty()){
            etvMobileNumber.setError("enter your mobile number");
            etvMobileNumber.requestFocus();
        }
        else if (mobileNumber.length() != 10){
            etvMobileNumber.setError("enter valid mobile number");
            etvMobileNumber.requestFocus();
        }
        else if (email.isEmpty()){
            etvEmail.setError("enter your E-mail");
            etvEmail.requestFocus();
        }

        else if (!tools.isValidEmail(email)){
            etvEmail.setError("enter valid email");
            etvEmail.requestFocus();
        }
        else if (password.isEmpty()){
            etvPassword.setError("enter your password");
            etvPassword.requestFocus();
        }
        else if (!tools.isValidPassword(password)){
            etvPassword.setError("enter valid password");
            etvPassword.requestFocus();
        }
        else if (confirmPassword.isEmpty()){
            etvConfirmPassword.setError("enter your confirm Password");
            etvConfirmPassword.requestFocus();
        }
        else if (password != confirmPassword){
            etvConfirmPassword.setError("confirm password dose not Match");
            etvConfirmPassword.requestFocus();
        }
        else {
            return true;
        }
        return true;
    }
}