package com.example.meetease.homeScreen.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.CarrierConfigManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meetease.R;

public class FaqActivity extends AppCompatActivity {

    TextView txtQ1,txtQ2,txtQ3,txtQ4;
    TextView txtA1,txtA2,txtA3,txtA4;
    LinearLayout lytQ1,lytQ2,lytQ3,lytQ4;
    ImageView imgUpDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqactivity);

        lytQ1 = findViewById(R.id.lytQ1);
        txtQ1 = findViewById(R.id.txtQ1);
        txtA1 = findViewById(R.id.txtA1);

        lytQ2 = findViewById(R.id.lytQ2);
        txtQ2 = findViewById(R.id.txtQ2);
        txtA2 = findViewById(R.id.txtA2);

        lytQ3 = findViewById(R.id.lytQ3);
        txtQ3 = findViewById(R.id.txtQ3);
        txtA3 = findViewById(R.id.txtA3);

        lytQ4 = findViewById(R.id.lytQ4);
        txtQ4 = findViewById(R.id.txtQ4);
        txtA4 = findViewById(R.id.txtA4);

        imgUpDown = findViewById(R.id.imgUpDown);

        txtQ1.setText("How do I book a boardroom using the app?");
        lytQ1.setOnClickListener(new View.OnClickListener() {
            boolean hideShow;
            @Override
            public void onClick(View view) {
                if (!hideShow) {
                    txtA1.setText("You can create a Reservation by Selecting Create a New Reservation. " +
                            "Select Desired Date and time Slot and Check the Availability for the rooms and " +
                            "Select any from the Room List.");
                    hideShow = true;
                    imgUpDown.setImageResource(R.drawable.up);
                } else {
                    txtA1.setText("");
                    hideShow = false;
                    imgUpDown.setImageResource(R.drawable.down);
                }
            }
        });

        txtQ2.setText("Can I book a Meeting Room for recurring meetings?");
        lytQ2.setOnClickListener(new View.OnClickListener() {
            boolean hideShow;
            @Override
            public void onClick(View view) {
                if (!hideShow) {
                    txtA2.setText("Yes, of Course.");
                    hideShow = true;
                    imgUpDown.setImageResource(R.drawable.up);
                } else {
                    txtA2.setText("");
                    hideShow = false;
                    imgUpDown.setImageResource(R.drawable.down);
                }
            }
        });

        txtQ3.setText("Is there a maximum duration for meeting room bookings?");
        lytQ3.setOnClickListener(new View.OnClickListener() {
            boolean hideShow;
            @Override
            public void onClick(View view) {
                if (!hideShow) {
                    txtA3.setText("No, There is no Maximum duration for bookings.");
                    hideShow = true;
                    imgUpDown.setImageResource(R.drawable.up);
                } else {
                    txtA3.setText("");
                    hideShow = false;
                    imgUpDown.setImageResource(R.drawable.down);
                }
            }
        });

        txtQ4.setText("Can I see the availability of Meeting Rooms in real-time?");
        lytQ4.setOnClickListener(new View.OnClickListener() {
            boolean hideShow;
            @Override
            public void onClick(View view) {
                if (!hideShow) {
                    txtA4.setText("Yes, Go to Create a New Reservation and Select Your Desired Date and Time.");
                    hideShow = true;
                    imgUpDown.setImageResource(R.drawable.up);
                } else {
                    txtA4.setText("");
                    hideShow = false;
                    imgUpDown.setImageResource(R.drawable.down);
                }
            }
        });
    }
}