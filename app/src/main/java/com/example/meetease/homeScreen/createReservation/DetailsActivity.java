package com.example.meetease.homeScreen.createReservation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;
import com.example.meetease.network.UserResponse;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DetailsActivity extends AppCompatActivity {

    ImageView imgRoom, imgFavourite,ivBack;
    TextView txtName, txtLocation, txtPrice;
    RatingBar ratingBar;
    Button btnBookNow;
    String checkFavourite;
    RestCall restCall;
    Tools tools;
    String roomId;
    PreferenceManager preferenceManager;
    String roomName, roomPrice, roomLocation, roomRating, roomImage;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imgRoom = findViewById(R.id.imgRoom);
        imgFavourite = findViewById(R.id.imgFavourite);
        ivBack = findViewById(R.id.ivBack);
        txtName = findViewById(R.id.txtName);
        txtLocation = findViewById(R.id.txtLocation);
        txtPrice = findViewById(R.id.txtPrice);
        ratingBar = findViewById(R.id.ratingBar);
        btnBookNow = findViewById(R.id.btnBookNow);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tools = new Tools(this);
        preferenceManager = new PreferenceManager(this);
        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        Intent intent = getIntent();
        roomName = intent.getStringExtra("roomName");
        roomPrice = intent.getStringExtra("roomPrice");
        roomLocation = intent.getStringExtra("roomLocation");
        roomRating = intent.getStringExtra("roomRating");
        roomImage = intent.getStringExtra("roomImage");
        roomId = intent.getStringExtra("roomId");

        txtLocation.setText(roomLocation);
        txtName.setText(roomName);
        txtPrice.setText(roomPrice + VariableBag.CURRENCY + "/Hour");
        ratingBar.setRating(Float.parseFloat(roomRating));

//        Glide
//                .with(this)
//                .load(roomImage)
//                .into(imgRoom);

        Tools.DisplayImage(DetailsActivity.this,imgRoom,roomImage);

        checkFavourite = "0";
        if (checkFavourite.equals("0")) {
            imgFavourite.setImageResource(R.drawable.baseline_favorite_border_24);
        } else {
            imgFavourite.setImageResource(R.drawable.baseline_favourite_24);
        }
        imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFavourite.equals("1")) {
                    imgFavourite.setImageResource(R.drawable.baseline_favorite_border_24);
                    checkFavourite = "0";
                } else {
                    addFavRoom();
                    checkFavourite = "1";
                }
            }
        });
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, PaymentActivity.class);
                intent.putExtra("roomName", roomName);
                intent.putExtra("roomPrice", roomPrice);
                intent.putExtra("roomLocation", roomLocation);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    void addFavRoom() {
        tools.showLoading();
        restCall.AddFavRoom("Addfavroom", roomId, preferenceManager.getKeyValueString(VariableBag.user_id, ""))
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
                                Toast.makeText(DetailsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (userResponse.getStatus().equals(VariableBag.SUCCESS_RESULT)) {
                                    tools.stopLoading();
                                    if (checkFavourite.equals("1")) {
                                        imgFavourite.setImageResource(R.drawable.baseline_favorite_border_24);
                                        checkFavourite = "0";
                                    } else {
                                        imgFavourite.setImageResource(R.drawable.baseline_favourite_24);
                                        checkFavourite = "1";
                                    }
                                }
                            }
                        });
                    }
                });
    }
}