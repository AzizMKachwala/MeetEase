package com.example.meetease.homeScreen.createReservation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.homeScreen.upComingMeeting.UpComingAdapter;
import com.example.meetease.homeScreen.upComingMeeting.UpComingMeetingActivity;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;
import com.example.meetease.network.UserResponse;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class PaymentActivity extends AppCompatActivity {

    TextView txtName, txtLocation, txtPrice, txtSelectedDate, txtTimeSlot, txtFinalPrice;
    String roomName, roomPrice, roomLocation, roomRating, roomId,bookingDate,bookingStartTime,bookingEndTime;
    Button btnPay;
    RestCall restCall;
    Tools tools;
    PreferenceManager preferenceManager;
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

        txtName.setText(roomName);
        txtLocation.setText(roomLocation);
        txtPrice.setText(roomPrice);
        txtSelectedDate.setText(bookingDate);
        txtTimeSlot.setText(bookingStartTime+" - "+bookingEndTime);
        preferenceManager = new PreferenceManager(this);



        tools = new Tools(this);
        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomBooking();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }

    private void roomBooking() {
        tools.showLoading();
        restCall.RoomBooking("AddTimeBooking", preferenceManager.getKeyValueString(VariableBag.user_id,""), roomId, bookingDate, bookingStartTime, bookingEndTime)
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

                                if (userResponse.getStatus().equals(VariableBag.SUCCESS_RESULT)){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        createNotificationChannel(PaymentActivity.this);
                                    }
                                    Intent resultIntent = new Intent(PaymentActivity.this, UpComingMeetingActivity.class);
                                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(PaymentActivity.this);
                                    stackBuilder.addNextIntentWithParentStack(resultIntent);
                                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                                    Notification notification = new NotificationCompat.Builder(PaymentActivity.this, "alarm_channel")
                                            .setContentTitle("Congratulations")
                                            .setContentText("Meeting Room is Booked SuccessFully")
                                            .setSmallIcon(R.drawable.img_meeting_rooms)
                                            .setContentIntent(resultPendingIntent)
                                            .build();
                                    NotificationManager notificationManager = (NotificationManager) PaymentActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                    notificationManager.notify(0, notification);
                                    Toast.makeText(PaymentActivity.this, "Booking successfully", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(PaymentActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(Context context) {
        NotificationChannel channel = new NotificationChannel("alarm_channel", "Alarm Channel", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Channel for alarm notifications");
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}