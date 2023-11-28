package com.example.meetease.homeScreen.createReservation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.meetease.R;
import com.example.meetease.appUtils.Tools;
import com.google.android.material.slider.RangeSlider;

import java.util.Locale;

public class FilterFragment extends DialogFragment {

    Button btnApply, btnReset;
    RatingBar ratingBar;
    TextView txtSelectedRange;
    RangeSlider priceRangeSlider;
    Spinner citySpinner;
    String selectedCity = "";
    String rating = "";
    String price = "";
    FilterApply filterApply;

    public interface FilterApply {
        void filterList(String city, String Price, String Rating);

        void reset();
    }

    public void setUpInterface(FilterApply filterApply) {
        this.filterApply = filterApply;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = new Dialog(getContext());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        btnApply = view.findViewById(R.id.btnApply);
        btnReset = view.findViewById(R.id.btnReset);
        ratingBar = view.findViewById(R.id.ratingBar);
        priceRangeSlider = view.findViewById(R.id.priceRangeSlider);
        citySpinner = view.findViewById(R.id.citySpinner);
        txtSelectedRange = view.findViewById(R.id.txtSelectedRange);

        String[] cityData = {"Select City", "Ahmedabad", "Rajkot", "Baroda", "Surat", "Mumbai", "Pune", "Banglore", "Bharuch", "Ghandhinagar"};
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cityData);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCity = (String) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        priceRangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                price = Tools.floatToInt(value) + "";
                txtSelectedRange.setText("0 - " + price);
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = Tools.floatToInt(v) + "";
            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterApply.filterList(selectedCity, price, rating);
                dismiss();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterApply.reset();
                dismiss();
            }
        });

        return view;
    }
}