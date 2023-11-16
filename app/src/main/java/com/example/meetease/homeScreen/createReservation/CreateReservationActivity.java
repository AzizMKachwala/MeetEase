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

    RecyclerView recyclerviewPreviousMeeting;
    CreateReservationAdapter createReservationAdapter;
    EditText etvSearch;
    TextView tvNoData;
    ImageView ivClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

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
                if (createReservationAdapter != null){
                    if (!etvSearch.getText().toString().isEmpty()){
                        ivClose.setVisibility(View.VISIBLE);
                    }
                    else {
                        ivClose.setVisibility(View.GONE);
                    }
                    createReservationAdapter.search(charSequence,tvNoData,recyclerviewPreviousMeeting);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        List<CreateReservationDataModel> list = new ArrayList<>();
        createReservationAdapter = new CreateReservationAdapter(list,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CreateReservationActivity.this);
        recyclerviewPreviousMeeting.setLayoutManager(layoutManager);
        recyclerviewPreviousMeeting.setAdapter(createReservationAdapter);

        createReservationAdapter.setUpInterFace(new CreateReservationAdapter.CreateReservationAdapterDataClick() {
            @Override
            public void bookDataClick(CreateReservationDataModel createReservationDataModel) {
                Intent intent = new Intent(CreateReservationActivity.this,BookMeetingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}