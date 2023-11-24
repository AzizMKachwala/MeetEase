package com.example.meetease.homeScreen.createReservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
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

        tools = new Tools(this);
        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(PaymentActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();

                roomBooking();
                Toast.makeText(PaymentActivity.this, "Booking Done", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void roomBooking() {
        tools.showLoading();
        restCall.RoomBooking("AddTimeBooking", "", "", "", "", "")
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
                                Toast.makeText(PaymentActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();

                            }
                        });
                    }
                });
    }
}