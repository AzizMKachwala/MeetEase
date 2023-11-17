package com.example.meetease.homeScreen.previousMeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetease.R;

import java.util.ArrayList;
import java.util.List;

public class PreviousMeetingActivity extends AppCompatActivity {

    RecyclerView recyclerviewPreviousMeeting;
    PreviousMeetingAdapter previousMeetingAdapter;
    EditText etvSearch;
    TextView tvNoData;
    ImageView ivClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_meeting);

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
        List<PreviousMeetingDataModel> list = new ArrayList<>();
        previousMeetingAdapter = new PreviousMeetingAdapter(list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PreviousMeetingActivity.this);
        recyclerviewPreviousMeeting.setLayoutManager(layoutManager);
        recyclerviewPreviousMeeting.setAdapter(previousMeetingAdapter);
    }
}