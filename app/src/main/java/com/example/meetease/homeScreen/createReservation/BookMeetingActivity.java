package com.example.meetease.homeScreen.createReservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.appUtils.Tools;

import java.util.Calendar;

public class BookMeetingActivity extends AppCompatActivity {

    TextView tvDate, tvStartTime, tvEndTime;
    View date, startTime, endTime;
    ImageView ivDate, ivStartTime, ivEndDate, ivBack;
    Button btnBookNow;
    DatePickerFragment datePickerFragment;
    StartTimePickerFragment startTimePickerFragment;
    EndTimePickerFragment endTimePickerFragment;
    String selectYear, selectMonth, selectDay, startMinute, startHour, endHour, endMinute;

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
        date = findViewById(R.id.date);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        ivBack = findViewById(R.id.ivBack);
        ivDate = findViewById(R.id.ivDate);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Calendar c = Calendar.getInstance();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerFragment = new DatePickerFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                datePickerFragment.show(fragmentTransaction, "#tag");
                datePickerFragment.setCancelable(false);

                datePickerFragment.setUpInterface(new DatePickerFragment.ButtonClick() {
                    @Override
                    public void saveClick(String date, String day, String month, String year) {
                        tvDate.setText(date);
                        selectDay = day;
                        selectMonth = month;
                        selectYear = year;
                    }
                });
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimePickerFragment = new StartTimePickerFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                startTimePickerFragment.show(fragmentTransaction, "#tag");
                startTimePickerFragment.setCancelable(false);

                startTimePickerFragment.setUpInterface(new StartTimePickerFragment.ButtonClick() {
                    @Override
                    public void saveClick(String Time, String hour, String min) {
                        tvStartTime.setText(Time);
                        startHour = hour;
                        startMinute = min;
                    }
                });
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimePickerFragment = new EndTimePickerFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                endTimePickerFragment.show(fragmentTransaction, "#tag");
                endTimePickerFragment.setCancelable(false);

                endTimePickerFragment.setUpInterface(new EndTimePickerFragment.ButtonClick() {
                    @Override
                    public void saveClick(String Time, String hour, String min) {
                        tvEndTime.setText(Time);
                        endHour = hour;
                        endMinute = min;
                    }
                });
            }
        });

        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvDate.getText().toString().equals("Select Date") || tvEndTime.getText().toString().isEmpty()) {
                    Tools.showCustomToast(getApplicationContext(), "Select Date First", findViewById(R.id.customToastLayout), getLayoutInflater());
                } else if (tvStartTime.getText().toString().equals("Select Start Time")) {
                    Tools.showCustomToast(getApplicationContext(), "Select Start Time", findViewById(R.id.customToastLayout), getLayoutInflater());
                } else if (tvEndTime.getText().toString().equals("Select End Time") || tvEndTime.getText().toString().isEmpty()) {
                    Tools.showCustomToast(getApplicationContext(), "Select End Time", findViewById(R.id.customToastLayout), getLayoutInflater());
                } else {
                    Intent intent = new Intent(BookMeetingActivity.this, CreateReservationActivity.class);
                    intent.putExtra("year", selectYear);
                    intent.putExtra("month", selectMonth);
                    intent.putExtra("day", selectDay);
                    intent.putExtra("startHour", startHour);
                    intent.putExtra("startMinute", startMinute);
                    intent.putExtra("endHour", endHour);
                    intent.putExtra("endMinute", endMinute);

                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

    }
}