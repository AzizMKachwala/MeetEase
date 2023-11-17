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
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.homeScreen.HomeScreenActivity;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;

public class LoginActivity extends AppCompatActivity {

    EditText etvEmailOrPhone,etvPassword;
    Button btnLogin;
    TextView txtResetPassword,txtSignup;
    ImageView imgPasswordCloseEye;
    String password = "Hide";
    RestCall restCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        etvEmailOrPhone = findViewById(R.id.etvEmailOrPhone);
        etvPassword = findViewById(R.id.etvPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignup = findViewById(R.id.txtSignup);
        txtResetPassword = findViewById(R.id.txtResetPassword);
        imgPasswordCloseEye= findViewById(R.id.imgPasswordCloseEye);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                PreferenceManager preferenceManager = new PreferenceManager(LoginActivity.this);
                preferenceManager.setKeyValueBoolean(VariableBag.SessionManage,true);
                startActivity(intent);
                finish();

            }
        });
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

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
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