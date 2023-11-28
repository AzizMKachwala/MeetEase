package com.example.meetease.homeScreen.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetease.R;
import com.example.meetease.homeScreen.createReservation.CreateReservationActivity;
import com.example.meetease.homeScreen.createReservation.CreateReservationAdapter;

public class NotificationActivity extends AppCompatActivity {

    ImageView ivBack;
    RecyclerView recyclerViewNotification;
    TextView tvNoData;
    SwipeRefreshLayout swipe;
    NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ivBack = findViewById(R.id.ivBack);
        tvNoData = findViewById(R.id.tvNoData);
        swipe = findViewById(R.id.swipe);
        recyclerViewNotification = findViewById(R.id.recyclerViewNotification);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
            }
        });

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this);
//        recyclerViewNotification.setLayoutManager(layoutManager);
//        notificationAdapter = new CreateReservationAdapter(apiList,NotificationActivity.this);
//        recyclerViewNotification.setAdapter(notificationAdapter);

    }
}