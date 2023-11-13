package com.example.meetease.homeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.meetease.R;

public class PreviousMeetingActivity extends AppCompatActivity {

    RecyclerView recyclerviewPreviousMeeting;
    PreviousMeetingAdapter previousMeetingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_meeting);
        recyclerviewPreviousMeeting = findViewById(R.id.recyclerviewPreviousMeeting);
        previousMeetingAdapter = new PreviousMeetingAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PreviousMeetingActivity.this);
        recyclerviewPreviousMeeting.setLayoutManager(layoutManager);
        recyclerviewPreviousMeeting.setAdapter(previousMeetingAdapter);
    }
}