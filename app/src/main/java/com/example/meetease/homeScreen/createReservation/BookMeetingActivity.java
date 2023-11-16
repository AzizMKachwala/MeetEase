package com.example.meetease.homeScreen.createReservation;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.meetease.R;

import java.util.Calendar;

public class BookMeetingActivity extends AppCompatActivity {

    TextView tvDate,tvStartTime,tvEndTime,tvRoomName,tvRoomNumber,tvBookingDate,tvBookingTime,tvPrice;
    ImageView ivDate,ivStartTime,ivEndDate;
    Button btnBookNow;
    public int mYear, mMonth,mDay,year,mHour, mMinute,month,day,startHour,startMinute,endHour,endMinute;
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
        ivDate = findViewById(R.id.ivDate);
        tvRoomName = findViewById(R.id.tvRoomName);
        tvRoomNumber = findViewById(R.id.tvRoomNumber);
        tvBookingDate = findViewById(R.id.tvBookingDate);
        tvBookingTime = findViewById(R.id.tvBookingTime);
        tvPrice = findViewById(R.id.tvPrice);
        tvBookingDate.setText("Not Selected");
        tvBookingTime.setText("Not Selected");
        tvPrice.setText("0 $");

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
                                month = monthOfYear+1;
                                day = dayOfMonth;
                                upDateDate();
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
                int a  = c.get(Calendar.AM_PM);
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookMeetingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                tvStartTime.setText(hourOfDay + ":" + minute);
                                startHour = hourOfDay;
                                startMinute = minute;
                                upDateDate();
                                tvEndTime.setText("");
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
                                upDateDate();
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvDate.getText().toString().equals("Select Date")){
                    Toast.makeText(BookMeetingActivity.this, "Select Date First", Toast.LENGTH_SHORT).show();
                }
                else if (tvStartTime.getText().toString().equals("tvStartTime")){
                    Toast.makeText(BookMeetingActivity.this, "Select StartTime First", Toast.LENGTH_SHORT).show();

                }
                else if (tvEndTime.getText().toString().equals("tvEndTime")){
                    Toast.makeText(BookMeetingActivity.this, "Select EndTime Second", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(BookMeetingActivity.this, "Booking SuccessFully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void upDateDate(){
        tvRoomName.setText("");
        tvRoomNumber.setText("");
        tvBookingDate.setText(day + "-" + (month + 1) + "-" + year);
        int hour = endHour - startHour;
        if(endMinute>startMinute){
            hour++;
        }
        tvBookingTime.setText(hour+" "+"Hour");
        tvPrice.setText(hour*100+" "+"$");
    }
}