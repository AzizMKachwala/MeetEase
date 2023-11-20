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
import com.example.meetease.dataModel.LoginDataModel;
import com.example.meetease.homeScreen.HomeScreenActivity;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;

import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    EditText etvEmailOrPhone,etvPassword;
    Button btnLogin;
    TextView txtResetPassword,txtSignup;
    ImageView imgPasswordCloseEye;
    String password = "Hide";
    RestCall restCall;
    Tools tools;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etvEmailOrPhone = findViewById(R.id.etvEmailOrPhone);
        etvPassword = findViewById(R.id.etvPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignup = findViewById(R.id.txtSignup);
        txtResetPassword = findViewById(R.id.txtResetPassword);
        imgPasswordCloseEye= findViewById(R.id.imgPasswordCloseEye);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        tools = new Tools(LoginActivity.this);
        preferenceManager = new PreferenceManager(LoginActivity.this);

        
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etvEmailOrPhone.getText().toString().trim().isEmpty()){
                    etvEmailOrPhone.setError("Enter Valid Email");
                    etvEmailOrPhone.requestFocus();
                }
                else if (etvPassword.getText().toString().trim().isEmpty()){
                    etvPassword.setError("Enter Valid Password");
                    etvPassword.requestFocus();
                }
                else {
                    loginUser();
                }
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

    private void loginUser() {
        tools.showLoading();
        restCall.LoginUser("LoginUser",etvEmailOrPhone.getText().toString().trim(),etvPassword.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<LoginDataModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(LoginDataModel loginDataModel) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Toast.makeText(LoginActivity.this, loginDataModel.getMessage(), Toast.LENGTH_SHORT).show();
                                if (loginDataModel.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {

                                    preferenceManager.setKeyValueBoolean(VariableBag.SessionManage,true);
                                    preferenceManager.setKeyValueString(VariableBag.user_id,loginDataModel.getUser_id());
                                    preferenceManager.setKeyValueString(VariableBag.full_name,loginDataModel.getFull_name());
                                    preferenceManager.setKeyValueString(VariableBag.mobile,loginDataModel.getMobile());
                                    preferenceManager.setKeyValueString(VariableBag.email,loginDataModel.getEmail());
                                    preferenceManager.setKeyValueString(VariableBag.password,etvPassword.getText().toString());
                                    startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class));
                                    finish();
                                }

                            }
                        });
                    }
                });
    }
}