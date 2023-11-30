package com.example.meetease.activity.homeScreen.mainScreen.create;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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

    ImageView imgRoom, imgFavourite, ivBack;
    TextView txtName, txtLocation, txtPrice;
    RatingBar ratingBar;
    Button btnBookNow;
    String checkFavourite;
    RestCall restCall;
    Tools tools;
    String roomId;
    PreferenceManager preferenceManager;
    int totalTime;
    String roomName, roomPrice, roomLocation, roomRating, roomImage, bookingDate, bookingStartTime, bookingEndTime;

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
        bookingDate = intent.getStringExtra("bookingDate");
        bookingStartTime = intent.getStringExtra("bookingStartTime");
        bookingEndTime = intent.getStringExtra("bookingEndTime");
        totalTime = intent.getIntExtra("totalTime",0);

        txtLocation.setText(roomLocation);
        txtName.setText(roomName);
        txtPrice.setText(roomPrice + VariableBag.CURRENCY + "/Hour");
        ratingBar.setRating(Float.parseFloat(roomRating));

        Tools.DisplayImage(DetailsActivity.this, imgRoom, roomImage);

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
                    deleteFavRoom();
                    imgFavourite.setImageResource(R.drawable.baseline_favorite_border_24);
                    checkFavourite = "0";
                } else {
                    addFavRoom();
                    imgFavourite.setImageResource(R.drawable.baseline_favourite_24);
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
                intent.putExtra("roomId", roomId);
                intent.putExtra("bookingDate", bookingDate);
                intent.putExtra("bookingStartTime", bookingStartTime);
                intent.putExtra("bookingEndTime", bookingEndTime);
                intent.putExtra("totalTime", totalTime);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    void addFavRoom() {
        tools.showLoading();
        restCall.AddFavRoom("AddFavRoom", roomId, preferenceManager.getKeyValueString(VariableBag.user_id, ""))
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
                                Tools.showCustomToast(getApplicationContext(), "No Internet", findViewById(R.id.customToastLayout), getLayoutInflater());
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

    void deleteFavRoom() {
        restCall.DeleteFavRoom("DeleteFavRoom", roomId, preferenceManager.getKeyValueString(VariableBag.user_id, ""))
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
                                Tools.showCustomToast(getApplicationContext(), "No Internet", findViewById(R.id.customToastLayout), getLayoutInflater());
                            }
                        });
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (userResponse.getStatus().equals(VariableBag.SUCCESS_RESULT)) {
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