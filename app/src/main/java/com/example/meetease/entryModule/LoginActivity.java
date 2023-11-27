package com.example.meetease.entryModule;

import androidx.annotation.NonNull;
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
import com.example.meetease.network.UserResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    EditText etvEmailOrPhone,etvPassword;
    Button btnLogin;
    TextView txtResetPassword,txtSignup;
    ImageView imgPasswordCloseEye;
    View viewGoogle;
    String flag = "1",name,mobileNumber,email;
    String password = "Hide";
    RestCall restCall;
    private static final int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    Tools tools;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etvEmailOrPhone = findViewById(R.id.etvEmailOrPhone);
        etvPassword = findViewById(R.id.etvPassword);
        btnLogin = findViewById(R.id.btnLogin);
        viewGoogle = findViewById(R.id.viewGoogle);
        txtSignup = findViewById(R.id.txtSignup);
        txtResetPassword = findViewById(R.id.txtResetPassword);
        imgPasswordCloseEye= findViewById(R.id.imgPasswordCloseEye);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        tools = new Tools(LoginActivity.this);
        preferenceManager = new PreferenceManager(LoginActivity.this);
//        preferenceManager.setKeyValueBoolean(VariableBag.SessionManage,true);

        viewGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });
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
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
    private void signInWithGoogle() {
        tools.showLoading();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            try {
                GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                tools.stopLoading();
                Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            tools.stopLoading();
                            FirebaseUser user = mAuth.getCurrentUser();
                            flag = "0";
                            name = user.getDisplayName();
                            email = user.getEmail();
                            mobileNumber = user.getPhoneNumber();
                            etvEmailOrPhone.setText(user.getEmail());
                            loginUser();
                        } else {
                            tools.stopLoading();
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginUser() {
        tools.showLoading();
        restCall.LoginUser("LoginUser",etvEmailOrPhone.getText().toString().trim(),etvPassword.getText().toString().trim(),flag)
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
                                flag="1";
                                etvEmailOrPhone.setText("");
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
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    finish();
                                }
                                else {
                                    tools.showLoading();
                                    AddUser();
                                }

                            }
                        });
                    }
                });
    }

    void AddUser(){
        restCall.AddUser("AddUser",name,email,"1234567890","GooglePassword2817")
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
                                Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                if(userResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)){
                                    loginUser();
                                }
                            }
                        });
                    }
                });

    }
}