package com.example.meetease.activity.homeScreen.mainScreen.create;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.meetease.R;
import com.example.meetease.activity.homeScreen.HomeScreenActivity;
import com.example.meetease.activity.homeScreen.settings.AvailableRoomsActivity;
import com.example.meetease.adapter.AllRoomsAdapter;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.dataModel.RoomDetailDataModel;
import com.example.meetease.dataModel.RoomDetailList;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;
import com.example.meetease.network.UserResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DetailsActivity extends AppCompatActivity {

    ImageView imgRoom, ivBack;
    TextView txtName, txtLocation, txtPrice;
    RatingBar ratingBar;
    Button btnBookNow;
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
        ivBack = findViewById(R.id.ivBack);
        txtName = findViewById(R.id.txtName);
        txtLocation = findViewById(R.id.txtLocation);
        txtPrice = findViewById(R.id.txtPrice);
        ratingBar = findViewById(R.id.ratingBar);
        btnBookNow = findViewById(R.id.btnBookNow);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this, HomeScreenActivity.class));
                finish();
            }
        });

        tools = new Tools(this);
        preferenceManager = new PreferenceManager(this);

        Intent intent = getIntent();
        if (intent.getExtras().getString(VariableBag.roomId) !=null){
            roomDetail(intent.getExtras().getString(VariableBag.roomId));
        }else {
            roomName = intent.getStringExtra("roomName");
            roomPrice = intent.getStringExtra("roomPrice");
            roomLocation = intent.getStringExtra("roomLocation");
            roomRating = intent.getStringExtra("roomRating");
            roomImage = intent.getStringExtra("roomImage");
            roomId = intent.getStringExtra("roomId");
            bookingDate = intent.getStringExtra("bookingDate");
            bookingStartTime = intent.getStringExtra("bookingStartTime");
            bookingEndTime = intent.getStringExtra("bookingEndTime");
            totalTime = intent.getIntExtra("totalTime", 0);
            txtLocation.setText(roomLocation);
            txtName.setText(roomName);
            txtPrice.setText(roomPrice + VariableBag.CURRENCY + "/Hour");
            ratingBar.setRating(Float.parseFloat(roomRating));
            Tools.DisplayImage(DetailsActivity.this, imgRoom, roomImage);

        }



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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DetailsActivity.this, HomeScreenActivity.class));
        finish();
    }

    void roomDetail(String roomId) {
        tools.showLoading();
        restCall.RoomDetails("GetRoomDetails")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RoomDetailDataModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();Tools.showCustomToast(getApplicationContext(), "No Internet", findViewById(R.id.customToastLayout), getLayoutInflater());
                            }
                        });
                    }

                    @Override
                    public void onNext(RoomDetailDataModel roomDetailDataModel) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (roomDetailDataModel.getStatus().equals(VariableBag.SUCCESS_RESULT) && roomDetailDataModel.getRoomDetailList() != null && !roomDetailDataModel.getRoomDetailList().isEmpty()) {
                                    RoomDetailList selectRoom = null;
                                    for (int i=0;i<roomDetailDataModel.getRoomDetailList().size();i++){
                                        if (roomDetailDataModel.getRoomDetailList().get(i).getRoom_d_id().equals(roomId)){
                                            selectRoom = roomDetailDataModel.getRoomDetailList().get(i);
                                        }
                                    }
                                    if (selectRoom != null){
                                        txtLocation.setText(selectRoom.getLocation());
                                        txtName.setText(selectRoom.getRoom_name());
                                        txtPrice.setText(selectRoom.getPrice() + VariableBag.CURRENCY + "/Hour");
                                        ratingBar.setRating(Float.parseFloat(selectRoom.getRating()));
                                        Tools.DisplayImage(DetailsActivity.this, imgRoom, selectRoom.getRoom_img());

                                        roomName = selectRoom.getRoom_name();
                                        roomPrice = intent.getStringExtra("roomPrice");
                                        roomLocation = intent.getStringExtra("roomLocation");
                                        roomRating = intent.getStringExtra("roomRating");
                                        roomImage = intent.getStringExtra("roomImage");
                                        bookingDate = intent.getStringExtra("bookingDate");
                                        bookingStartTime = intent.getStringExtra("bookingStartTime");
                                        bookingEndTime = intent.getStringExtra("bookingEndTime");
                                        totalTime = intent.getIntExtra("totalTime", 0);
                                    }

                                }
                            }
                        });
                    }
                });
    }
}