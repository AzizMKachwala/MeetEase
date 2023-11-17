package com.example.meetease.homeScreen.createReservation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.homeScreen.HomeScreenActivity;

import java.util.Calendar;

public class BookMeetingActivity extends AppCompatActivity {

    TextView tvDate, tvStartTime, tvEndTime, tvRoomName, tvRoomNumber, tvBookingDate, tvBookingTime, tvPrice;
    ImageView ivDate, ivStartTime, ivEndDate, ivBack;
    Button btnBookNow;

    String roomName, roomPrice, roomLocation;

    public int mYear, mMonth, mDay, year, mHour, mMinute, month, day, startHour, startMinute, endHour, endMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_meeting);

        tvDate = findViewById(R.id.tvDate);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        ivStartTime = findViewById(R.id.ivStartTime);
        ivEndDate = findViewById(R.id.ivEndTime);
        btnBookNow = findViewById(R.id.btnBookNow);
        ivBack = findViewById(R.id.ivBack);
        ivDate = findViewById(R.id.ivDate);
        tvRoomName = findViewById(R.id.tvRoomName);
        tvRoomNumber = findViewById(R.id.tvRoomNumber);
        tvBookingDate = findViewById(R.id.tvBookingDate);
        tvBookingTime = findViewById(R.id.tvBookingTime);
        tvPrice = findViewById(R.id.tvPrice);
        tvBookingDate.setText("Not Selected");
        tvBookingTime.setText("Not Selected");
        tvPrice.setText("0 ₹");

        Intent intent = getIntent();
        roomName = intent.getStringExtra("roomName");
        roomPrice = intent.getStringExtra("roomPrice");
        roomLocation = intent.getStringExtra("roomLocation");

        tvRoomNumber.setText(roomLocation);
        tvRoomName.setText(roomName);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookMeetingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {
                                tvDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);
                                year = year1;
                                month = monthOfYear + 1;
                                day = dayOfMonth;
                                updateDate();
                                tvStartTime.setText("Select Start Time");
                                tvEndTime.setText("Select End Time");
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                int a = c.get(Calendar.AM_PM);
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookMeetingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                tvStartTime.setText(hourOfDay + ":" + minute);
                                startHour = hourOfDay;
                                startMinute = minute;
                                updateDate();
                                tvEndTime.setText("Select End Time");
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookMeetingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                tvEndTime.setText(hourOfDay + ":" + minute);
                                endHour = hourOfDay;
                                endMinute = minute;
                                updateDate();
                            }
                        }, mHour, mMinute, false);

                timePickerDialog.show();
            }
        });
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvDate.getText().toString().equals("Select Date") || tvEndTime.getText().toString().isEmpty()) {
                    Toast.makeText(BookMeetingActivity.this, "Select Date First", Toast.LENGTH_SHORT).show();
                } else if (tvStartTime.getText().toString().equals("Select Start Time")) {
                    Toast.makeText(BookMeetingActivity.this, "Select Start Time First", Toast.LENGTH_SHORT).show();
                } else if (tvEndTime.getText().toString().equals("Select End Time") || tvEndTime.getText().toString().isEmpty()) {
                    Toast.makeText(BookMeetingActivity.this, "Select End Time Second", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookMeetingActivity.this, "Booking SuccessFully", Toast.LENGTH_SHORT).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        createNotificationChannel(BookMeetingActivity.this);
                    }
                    Intent resultIntent = new Intent(BookMeetingActivity.this, HomeScreenActivity.class);
                    resultIntent.putExtra("action", "action");
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(BookMeetingActivity.this);
                    stackBuilder.addNextIntentWithParentStack(resultIntent);
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                    Notification notification = new NotificationCompat.Builder(BookMeetingActivity.this, "alarm_channel")
                            .setContentTitle("Congratulations")
                            .setContentText("Meeting Room is Booked SuccessFully")
                            .setSmallIcon(R.drawable.img_meeting_rooms)
                            .setContentIntent(resultPendingIntent)
                            .build();
                    NotificationManager notificationManager = (NotificationManager) BookMeetingActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                }
            }
        });
    }

    void updateDate() {
        tvBookingDate.setText(day + "-" + (month) + "-" + year);
        int hour = endHour - startHour;
        if (endMinute > startMinute) {
            hour++;
        }
        tvBookingTime.setText(hour + " " + "Hour");
        tvPrice.setText(hour * Integer.parseInt(roomPrice) + " " + VariableBag.CURRENCY);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(Context context) {
        NotificationChannel channel = new NotificationChannel("alarm_channel", "Alarm Channel", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Channel for alarm notifications");
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}