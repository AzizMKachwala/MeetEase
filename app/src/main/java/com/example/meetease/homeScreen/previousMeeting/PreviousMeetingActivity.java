package com.example.meetease.homeScreen.previousMeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.dataModel.RoomDetailDataModel;
import com.example.meetease.dataModel.RoomDetailList;
import com.example.meetease.entryModule.SignUpActivity;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class PreviousMeetingActivity extends AppCompatActivity {

    RecyclerView recyclerviewPreviousMeeting;
    PreviousMeetingAdapter previousMeetingAdapter;
    EditText etvSearch;
    TextView tvNoData;
    ImageView ivClose;
    RestCall restCall;
    Tools tools;

    String tag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_meeting);

        Intent intent = getIntent();
        if (intent.getStringExtra("abc")!=null){
            tag = intent.getStringExtra("abc");
        }

        tools = new Tools(this);
        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        recyclerviewPreviousMeeting = findViewById(R.id.recyclerviewPreviousMeeting);
        etvSearch = findViewById(R.id.etvSearch);
        tvNoData = findViewById(R.id.tvNoData);
        ivClose = findViewById(R.id.ivClose);

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
                    previousMeetingAdapter.search(charSequence, tvNoData, recyclerviewPreviousMeeting);
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
                                Toast.makeText(PreviousMeetingActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(RoomDetailDataModel roomDetailDataModel) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (roomDetailDataModel.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)){
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PreviousMeetingActivity.this);
                                    recyclerviewPreviousMeeting.setLayoutManager(layoutManager);
                                    ArrayList<RoomDetailList> newList = new ArrayList<>();
                                    if (tag.equals("favoriteRoom")){
                                        for (int i=0;i<roomDetailDataModel.getRoomDetailList().size();i++){
                                            if (roomDetailDataModel.getRoomDetailList().get(i).getFavorite_room().equals(0)){
                                                newList.add(roomDetailDataModel.getRoomDetailList().get(i));
                                            }
                                        }
                                    }else {

                                        for (int i=0;i<roomDetailDataModel.getRoomDetailList().size();i++){
                                            if (roomDetailDataModel.getRoomDetailList().get(i).getUpcoming_status().equals("0")){
                                                newList.add(roomDetailDataModel.getRoomDetailList().get(i));
                                            }
                                        }
                                    }
                                    previousMeetingAdapter = new PreviousMeetingAdapter(newList,PreviousMeetingActivity.this);
                                    recyclerviewPreviousMeeting.setAdapter(previousMeetingAdapter);
                                }
                            }
                        });
                    }
                });
    }
}