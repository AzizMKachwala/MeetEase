package com.example.meetease.homeScreen.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.entryModule.SignUpActivity;
import com.example.meetease.homeScreen.HomeScreenActivity;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;
import com.example.meetease.network.UserResponse;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SecurityActivity extends AppCompatActivity {

    PreferenceManager preferenceManager;
    SwitchCompat switchOnOff;
    ImageView ivBack;
    ConstraintLayout lytChangePassword, lytDeleteUser;
    RestCall restCall;
    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        preferenceManager = new PreferenceManager(this);
        switchOnOff = findViewById(R.id.switchOnOff);
        ivBack = findViewById(R.id.ivBack);
        lytChangePassword = findViewById(R.id.lytChangePassword);
        lytDeleteUser = findViewById(R.id.lytDeleteUser);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        preferenceManager = new PreferenceManager(SecurityActivity.this);
        tools = new Tools(SecurityActivity.this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lytChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecurityActivity.this, ChangePasswordActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        lytDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SecurityActivity.this);
                builder.setMessage("Are You Sure You Want To Delete?");
                builder.setTitle("Alert !!");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                    deleteUser(preferenceManager.getKeyValueString(VariableBag.user_id, ""));
                });
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        switchOnOff.setChecked(preferenceManager.getKeyValueBoolean(VariableBag.SecuritySwitchCheck));

        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean status) {
                preferenceManager.setKeyValueBoolean(VariableBag.SecuritySwitchCheck, status);
            }
        });
    }

    private void deleteUser(String userId) {
        tools.showLoading();
        restCall.deleteUser("deleteuser1", userId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<UserResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        tools.stopLoading();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SecurityActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        tools.stopLoading();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (userResponse.getStatus().equals(VariableBag.SUCCESS_RESULT)) {
                                    Toast.makeText(SecurityActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SecurityActivity.this, SignUpActivity.class));
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    finish();
                                }
                            }
                        });
                    }
                });
    }
}