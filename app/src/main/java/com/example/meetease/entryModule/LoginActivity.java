package com.example.meetease.entryModule;

import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {

    EditText etvEmailOrPhone,etvPassword;
    Button btnLogin;
    TextView txtResetPassword;
    ImageView imgPasswordCloseEye;
    String password = "Hide";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etvEmailOrPhone = findViewById(R.id.etvEmailOrPhone);
        etvPassword = findViewById(R.id.etvPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtResetPassword = findViewById(R.id.txtResetPassword);
        imgPasswordCloseEye= findViewById(R.id.imgPasswordCloseEye);

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

        txtResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "RESET PASSWORD", Toast.LENGTH_SHORT).show();
            }
        });
    }
}