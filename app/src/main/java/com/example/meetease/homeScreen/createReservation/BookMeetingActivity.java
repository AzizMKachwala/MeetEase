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

    TextView tvDate, tvStartTime, tvEndTime;
    ImageView ivDate, ivStartTime, ivEndDate, ivBack;
    Button btnBookNow;
    private int minHour = 8, minMinute = 0, mYear, mMonth, mDay, year, mHour, mMinute, month, day, startHour, startMinute, endHour, endMinute;

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
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final Calendar c = Calendar.getInstance();
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookMeetingActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {
                                tvDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);
                                year = year1;
                                month = monthOfYear + 1;
                                day = dayOfMonth;
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tvStartTime.setText(hourOfDay + ":" + minute);
                        startHour = hourOfDay;
                        startMinute = minute;
                        minHour = hourOfDay;
                        minMinute = minute;
                        tvEndTime.setText("Select End Time");
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });
        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay < minHour || (hourOfDay == minHour && minute < minMinute)) {
                            Toast.makeText(BookMeetingActivity.this, "End Time Should Be After " + minHour + ":" + minMinute, Toast.LENGTH_SHORT).show();
                        } else {
                            endHour = hourOfDay;
                            endMinute = minute;
                            tvEndTime.setText(hourOfDay + ":" + minute);
                        }
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvDate.getText().toString().equals("Select Date") || tvEndTime.getText().toString().isEmpty()) {
                    Toast.makeText(BookMeetingActivity.this, "Select Date First", Toast.LENGTH_SHORT).show();
                } else if (tvStartTime.getText().toString().equals("Select Start Time")) {
                    Toast.makeText(BookMeetingActivity.this, "Select Start Time", Toast.LENGTH_SHORT).show();
                } else if (tvEndTime.getText().toString().equals("Select End Time") || tvEndTime.getText().toString().isEmpty()) {
                    Toast.makeText(BookMeetingActivity.this, "Select End Time", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(BookMeetingActivity.this, CreateReservationActivity.class);
                    intent.putExtra("year", String.valueOf(year));
                    intent.putExtra("month", String.valueOf(month));
                    intent.putExtra("day", String.valueOf(day));
                    intent.putExtra("startHour", String.valueOf(startHour));
                    intent.putExtra("startMinute", String.valueOf(startMinute));
                    intent.putExtra("endHour", String.valueOf(endHour));
                    intent.putExtra("endMinute", String.valueOf(endMinute));
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }
}