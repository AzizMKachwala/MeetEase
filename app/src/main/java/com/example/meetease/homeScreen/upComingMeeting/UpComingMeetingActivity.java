package com.example.meetease.homeScreen.upComingMeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.appUtils.PreferenceManager;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.dataModel.RoomDetailDataModel;
import com.example.meetease.dataModel.UpComingResponse;
import com.example.meetease.homeScreen.previousMeeting.PreviousMeetingActivity;
import com.example.meetease.homeScreen.previousMeeting.PreviousMeetingAdapter;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class UpComingMeetingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PreviousMeetingAdapter previousMeetingAdapter;
    EditText etvSearch;
    TextView tvNoData;
    ImageView ivClose;
    RestCall restCall;
    Tools tools;
    SwipeRefreshLayout swipeRefreshLayout;
    UpComingAdapter upComingAdapter;
    PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming_meeting);
        tools = new Tools(this);
        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        recyclerView = findViewById(R.id.recyclerviewUpComingMeeting);
        etvSearch = findViewById(R.id.etvSearch);
        tvNoData = findViewById(R.id.tvNoData);
        ivClose = findViewById(R.id.ivClose);
        swipeRefreshLayout = findViewById(R.id.swipe);
        preferenceManager = new PreferenceManager(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                roomDetail();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        ivClose.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);

        ivClose.setOnClickListener(view -> {
            ivClose.setVisibility(View.GONE);
            etvSearch.setText("");
        });
        etvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (previousMeetingAdapter != null) {
                    if (!etvSearch.getText().toString().isEmpty()) {
                        ivClose.setVisibility(View.VISIBLE);
                    } else {
                        ivClose.setVisibility(View.GONE);
                    }
                    previousMeetingAdapter.search(charSequence, tvNoData, recyclerView);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tools.showLoading();
        roomDetail();

    }

    void roomDetail() {
        tools.showLoading();
        restCall.UpcomingBookings("UpcomingBookings",preferenceManager.getKeyValueString(VariableBag.user_id,""))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<UpComingResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Toast.makeText(UpComingMeetingActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(UpComingResponse upComingResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Toast.makeText(UpComingMeetingActivity.this, upComingResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                if (upComingResponse.getStatus().equals(VariableBag.SUCCESS_RESULT)&&upComingResponse.getUpComingListResponses() != null && upComingResponse.getUpComingListResponses().size()>0){
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(UpComingMeetingActivity.this);
                                    upComingAdapter = new UpComingAdapter(upComingResponse.getUpComingListResponses(),UpComingMeetingActivity.this);
                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setAdapter(upComingAdapter);
                                }
                                else {
                                    tvNoData.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                });
    }
}