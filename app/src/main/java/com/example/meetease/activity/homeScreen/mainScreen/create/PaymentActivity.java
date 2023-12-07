package com.example.meetease.activity.homeScreen.mainScreen.create;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.activity.homeScreen.HomeScreenActivity;
import com.example.meetease.activity.homeScreen.mainScreen.UpComingMeetingActivity;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.fragment.StartTimePickerFragment;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;
import com.example.meetease.network.UserResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class PaymentActivity extends AppCompatActivity {

    TextView txtName, txtLocation, txtPrice, txtSelectedDate, txtTimeSlot, txtFinalPrice;
    Button btnPay;
    ImageView ivBack;
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
        ivBack = findViewById(R.id.ivBack);

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

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtName.setText(roomName);
        txtLocation.setText(roomLocation);
        txtPrice.setText(roomPrice);
        txtSelectedDate.setText(bookingDate);
        txtTimeSlot.setText(bookingStartTime + " - " + bookingEndTime);
        txtFinalPrice.setText("" + Integer.parseInt(roomPrice) * totalTime);

        btnPay.setText(" Pay    --->    " + Integer.parseInt(roomPrice) * totalTime + VariableBag.CURRENCY);
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
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    createNotificationChannel(PaymentActivity.this);
                                }
                                if (userResponse.getStatus().equals(VariableBag.SUCCESS_RESULT)) {
                                    Intent resultIntent = new Intent(PaymentActivity.this, UpComingMeetingActivity.class);
                                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(PaymentActivity.this);
                                    stackBuilder.addNextIntentWithParentStack(resultIntent);
                                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                                    Notification notification = new NotificationCompat.Builder(PaymentActivity.this, "alarm_channel")
                                            .setContentTitle("Congratulation")
                                            .setContentText("Your Meeting Room Is Booked - " + roomName)
                                            .setSmallIcon(R.drawable.bg)
                                            .setContentIntent(resultPendingIntent)
                                            .build();
                                    NotificationManager notificationManager = (NotificationManager) PaymentActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                    notificationManager.notify(0, notification);
                                    startActivity(new Intent(PaymentActivity.this, PaymentSuccessActivity.class));
                                    finish();
                                }
                            }
                        });
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(Context context) {
        NotificationChannel channel = new NotificationChannel("meeting_notification", "meeting_notification", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Channel for meeting notifications");
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
