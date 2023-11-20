package com.example.meetease.entryModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.homeScreen.HomeScreenActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        PreferenceManager preferenceManager = new PreferenceManager(SplashScreenActivity.this);
//        if (preferenceManager.getKeyValueBoolean(VariableBag.SessionManage)){
//            Intent intent = new Intent(SplashScreenActivity.this, HomeScreenActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }
}