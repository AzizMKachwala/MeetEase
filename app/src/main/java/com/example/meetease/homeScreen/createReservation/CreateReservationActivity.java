package com.example.meetease.homeScreen.createReservation;

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

import com.example.meetease.R;
import com.example.meetease.homeScreen.previousMeeting.PreviousMeetingActivity;
import com.example.meetease.homeScreen.previousMeeting.PreviousMeetingAdapter;
import com.example.meetease.homeScreen.previousMeeting.PreviousMeetingDataModel;

import java.util.ArrayList;
import java.util.List;

public class CreateReservationActivity extends AppCompatActivity {

    RecyclerView recyclerViewMeetingRooms;
    CreateReservationAdapter createReservationAdapter;
    EditText etvSearch;
    TextView tvNoData;
    ImageView ivClose;
    List<CreateReservationDataModel> roomList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

        roomList = new ArrayList<>();
        addData();
        recyclerViewMeetingRooms = findViewById(R.id.recyclerViewMeetingRooms);
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
                if (createReservationAdapter != null){
                    if (!etvSearch.getText().toString().isEmpty()){
                        ivClose.setVisibility(View.VISIBLE);
                    }
                    else {
                        ivClose.setVisibility(View.GONE);
                    }
                    createReservationAdapter.search(charSequence,tvNoData,recyclerViewMeetingRooms);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        createReservationAdapter = new CreateReservationAdapter(roomList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CreateReservationActivity.this);
        recyclerViewMeetingRooms.setLayoutManager(layoutManager);
        recyclerViewMeetingRooms.setAdapter(createReservationAdapter);

        createReservationAdapter.setUpInterFace(new CreateReservationAdapter.CreateReservationAdapterDataClick() {
            @Override
            public void bookDataClick(CreateReservationDataModel createReservationDataModel) {
                Intent intent = new Intent(CreateReservationActivity.this,DetailsActivity.class);
                intent.putExtra("roomName",createReservationDataModel.roomName);
                intent.putExtra("roomPrice",createReservationDataModel.roomPrice);
                intent.putExtra("roomLocation",createReservationDataModel.roomLocation);
                intent.putExtra("roomRating",createReservationDataModel.roomRating);
                intent.putExtra("roomImage",createReservationDataModel.roomImage);
                startActivity(intent);
            }
        });
    }
    void addData(){
        roomList.add(new CreateReservationDataModel("hotel taj","Ahmedabad","1000","4","https://ballantyneexecutivesuites.com/wp-content/uploads/2015/10/Depositphotos_13534536_original.jpg"));
        roomList.add(new CreateReservationDataModel("hotel raj","Rajkot","2000","5","https://ballantyneexecutivesuites.com/wp-content/uploads/2015/10/Depositphotos_13534536_original.jpg"));
        roomList.add(new CreateReservationDataModel("hotel Sky","surat","500","3","https://ballantyneexecutivesuites.com/wp-content/uploads/2015/10/Depositphotos_13534536_original.jpg"));
        roomList.add(new CreateReservationDataModel("hotel hill","Gandinagar","1500","4","https://images.pexels.com/photos/2976970/pexels-photo-2976970.jpeg"));
        roomList.add(new CreateReservationDataModel("hotel taj","Baroda","300","2","https://images.pexels.com/photos/2976970/pexels-photo-2976970.jpeg"));
    }
}