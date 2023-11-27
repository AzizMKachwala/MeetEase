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

    TextView txtQ1,txtQ2,txtQ3;
    TextView txtA1,txtA2,txtA3;
    LinearLayout lytQ1,lytQ2,lytQ3;
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

        imgUpDown = findViewById(R.id.imgUpDown);

        lytQ1.setOnClickListener(new View.OnClickListener() {
            boolean hideShow;
            @Override
            public void onClick(View view) {
                if (!hideShow) {
                    txtA1.setText("ABCDEFGHIJKLMNOPQRSTUVXYZ");
                    hideShow = true;
                    imgUpDown.setImageResource(R.drawable.up);
                } else {
                    txtA1.setText("");
                    hideShow = false;
                    imgUpDown.setImageResource(R.drawable.down);
                }
            }
        });

        lytQ2.setOnClickListener(new View.OnClickListener() {
            boolean hideShow;
            @Override
            public void onClick(View view) {
                if (!hideShow) {
                    txtA2.setText("ABCDEFGHIJKLMNOPQRSTUVXYZ");
                    hideShow = true;
                    imgUpDown.setImageResource(R.drawable.up);
                } else {
                    txtA2.setText("");
                    hideShow = false;
                    imgUpDown.setImageResource(R.drawable.down);
                }
            }
        });

        lytQ3.setOnClickListener(new View.OnClickListener() {
            boolean hideShow;
            @Override
            public void onClick(View view) {
                if (!hideShow) {
                    txtA3.setText("ABCDEFGHIJKLMNOPQRSTUVXYZ");
                    hideShow = true;
                    imgUpDown.setImageResource(R.drawable.up);
                } else {
                    txtA3.setText("");
                    hideShow = false;
                    imgUpDown.setImageResource(R.drawable.down);
                }
            }
        });
    }
}