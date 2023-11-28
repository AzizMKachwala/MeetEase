package com.example.meetease.homeScreen.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.dataModel.RoomDetailDataModel;
import com.example.meetease.homeScreen.createReservation.BookMeetingActivity;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class AvailableRoomsActivity extends AppCompatActivity {

    RecyclerView recyclerViewAllRooms;
    View btnBookNow;
    TextView tvNoData;
    RestCall restCall;
    ImageView ivBack;
    Tools tools;
    AllRoomsAdapter allRoomsAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_rooms);

        btnBookNow = findViewById(R.id.btnBookNow);
        recyclerViewAllRooms = findViewById(R.id.recyclerViewAllRooms);
        swipeRefreshLayout = findViewById(R.id.swipe);
        tvNoData = findViewById(R.id.tvNoData);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvNoData.setVisibility(View.GONE);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                roomDetail();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        tools = new Tools(this);
        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AvailableRoomsActivity.this, BookMeetingActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        roomDetail();
    }

    void roomDetail() {
        tools.showLoading();
        restCall.RoomDetails("getRoom")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RoomDetailDataModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Log.e("##error", e.getLocalizedMessage());
                                Toast.makeText(AvailableRoomsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                tvNoData.setVisibility(View.VISIBLE);
                            }
                        });
                    }

                    @Override
                    public void onNext(RoomDetailDataModel roomDetailDataModel) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (roomDetailDataModel.getStatus().equals(VariableBag.SUCCESS_RESULT) && roomDetailDataModel.getRoomDetailList() != null && !roomDetailDataModel.getRoomDetailList().isEmpty()) {
                                    tvNoData.setVisibility(View.GONE);
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AvailableRoomsActivity.this);
                                    recyclerViewAllRooms.setLayoutManager(layoutManager);
                                    allRoomsAdapter = new AllRoomsAdapter(roomDetailDataModel.getRoomDetailList(), AvailableRoomsActivity.this);
                                    recyclerViewAllRooms.setAdapter(allRoomsAdapter);
                                }
                            }
                        });
                    }
                });
    }
}