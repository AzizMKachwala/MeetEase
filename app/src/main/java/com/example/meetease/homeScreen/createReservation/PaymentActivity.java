package com.example.meetease.homeScreen.createReservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.homeScreen.HomeScreenActivity;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;
import com.example.meetease.network.UserResponse;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class PaymentActivity extends AppCompatActivity {

    TextView txtName, txtLocation, txtPrice, txtSelectedDate, txtTimeSlot, txtFinalPrice;
    Button btnPay;
    RestCall restCall;
    Tools tools;
    PreferenceManager preferenceManager;
    int totalTime;
    String roomName, roomPrice, roomLocation, roomRating, roomId, bookingDate, bookingStartTime, bookingEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        txtName = findViewById(R.id.txtName);
        txtLocation = findViewById(R.id.txtLocation);
        txtPrice = findViewById(R.id.txtPrice);
        txtSelectedDate = findViewById(R.id.txtSelectedDate);
        txtTimeSlot = findViewById(R.id.txtTimeSlot);
        txtFinalPrice = findViewById(R.id.txtFinalPrice);
        btnPay = findViewById(R.id.btnPay);

        Intent intent = getIntent();
        roomName = intent.getStringExtra("roomName");
        roomPrice = intent.getStringExtra("roomPrice");
        roomLocation = intent.getStringExtra("roomLocation");
        roomRating = intent.getStringExtra("roomRating");
        roomId = intent.getStringExtra("roomId");
        bookingDate = intent.getStringExtra("bookingDate");
        bookingStartTime = intent.getStringExtra("bookingStartTime");
        bookingEndTime = intent.getStringExtra("bookingEndTime");
        totalTime = intent.getIntExtra("totalTime", 0);

        txtName.setText(roomName);
        txtLocation.setText(roomLocation);
        txtPrice.setText(roomPrice);
        txtSelectedDate.setText(bookingDate);
        txtTimeSlot.setText(bookingStartTime + " - " + bookingEndTime);
        txtFinalPrice.setText("" + Integer.parseInt(roomPrice) * totalTime);

        preferenceManager = new PreferenceManager(this);
        tools = new Tools(this);
        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomBooking();
            }
        });
    }

    private void roomBooking() {
        tools.showLoading();
        restCall.RoomBooking("AddTimeBooking", preferenceManager.getKeyValueString(VariableBag.user_id, ""), roomId, bookingDate, bookingStartTime, bookingEndTime)
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
                                Tools.showCustomToast(getApplicationContext(), "No Internet", findViewById(R.id.customToastLayout), getLayoutInflater());
                            }
                        });
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Tools.showCustomToast(getApplicationContext(), userResponse.getMessage(), findViewById(R.id.customToastLayout), getLayoutInflater());
                                if (userResponse.getStatus().equals(VariableBag.SUCCESS_RESULT)) {
                                    startActivity(new Intent(PaymentActivity.this, HomeScreenActivity.class));
                                    finish();
                                }
                            }
                        });
                    }
                });
    }
}
