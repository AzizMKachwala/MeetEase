package com.example.meetease.activity.entryModule;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetease.R;

public class GuideActivity extends AppCompatActivity {

    Button btnNext, btnSkip;
    ImageView imgSteps, ivBack;
    TextView txtSteps;
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        btnNext = findViewById(R.id.btnNext);
        imgSteps = findViewById(R.id.imgSteps);
        txtSteps = findViewById(R.id.txtSteps);
        btnSkip = findViewById(R.id.btnSkip);
        ivBack = findViewById(R.id.ivBack);

        txtSteps.setText("Step 1:\nClick 'Create a New Reservation'.");
//        imgSteps.setImageResource(R.drawable.home_screen_image);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos == 0) {
                    txtSteps.setText("Step 2:\nSelect Your Desired Date & Time For the Meeting Room.");
//                    imgSteps.setImageResource(R.drawable.selector_screen_image);
                    pos++;
                } else if (pos == 1) {
                    txtSteps.setText("Step 3:\nSelect the Meeting Room of your choice from the List.");
//                    imgSteps.setImageResource(R.drawable.show_room_screen_image);
                    pos++;
                } else if (pos == 2) {
                    txtSteps.setText("Step 4:\nIf you have some Specific requirements,\nChoose the requirements in the filter.");
//                    imgSteps.setImageResource(R.drawable.filter_screen_image);
                    pos++;
                } else if (pos == 3) {
                    txtSteps.setText("Step 5:\nSelect the Meeting Room to see the Details.");
//                    imgSteps.setImageResource(R.drawable.show_room_detail_screen);
                    pos++;
                } else if (pos == 4) {
                    txtSteps.setText("Step 6:\nComplete the Payment Process and wait for the Successful Message.");
//                    imgSteps.setImageResource(R.drawable.show_room_detail_screen);
                    btnNext.setText("Finish");
                    pos++;
                    pos++;
                } else {
                    finish();
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }
}