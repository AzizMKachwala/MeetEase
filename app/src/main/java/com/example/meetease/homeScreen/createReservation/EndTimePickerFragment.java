package com.example.meetease.homeScreen.createReservation;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.meetease.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EndTimePickerFragment extends DialogFragment {

    TimePicker timePicker;
    Button btnCancel, btnSave;
    String SelectedEndTime;
    ButtonClick buttonClick;
    SimpleDateFormat sdf;
    String selectMin, selectHour;

    public interface ButtonClick {
        void saveClick(String Time, String hour, String min);
    }

    public void setUpInterface(ButtonClick buttonClick) {
        this.buttonClick = buttonClick;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_end_time_picker, container, false);

        timePicker = view.findViewById(R.id.timePicker);
        btnSave = view.findViewById(R.id.btnSave);
        btnCancel = view.findViewById(R.id.btnCancel);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                StringBuilder builder = new StringBuilder();
                builder.append(timePicker.getHour() + ":");
                builder.append(timePicker.getMinute());
                SelectedEndTime = builder.toString();

                Calendar selectedTime = Calendar.getInstance();
                selectedTime.set(Calendar.HOUR_OF_DAY, hour);
                selectedTime.set(Calendar.MINUTE, minute);
                sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                SelectedEndTime = sdf.format(selectedTime.getTime());
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder builder = new StringBuilder();
                builder.append(timePicker.getHour() + ":");
                builder.append(timePicker.getMinute());
                SelectedEndTime = builder.toString();
                buttonClick.saveClick(SelectedEndTime, String.valueOf(timePicker.getHour()), String.valueOf(timePicker.getMinute()));
                dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }
}