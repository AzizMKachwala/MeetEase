package com.example.meetease.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.appUtils.Tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReceiptFragment extends DialogFragment {

    TextView txtName, txtLocation, txtPrice, txtSelectedDate, txtTimeSlot, txtFinalPrice;
    ImageView imgPdf, imgSs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);

        txtName = view.findViewById(R.id.txtName);
        txtLocation = view.findViewById(R.id.txtLocation);
        txtPrice = view.findViewById(R.id.txtPrice);
        txtSelectedDate = view.findViewById(R.id.txtSelectedDate);
        txtTimeSlot = view.findViewById(R.id.txtTimeSlot);
        txtFinalPrice = view.findViewById(R.id.txtFinalPrice);

        imgSs = view.findViewById(R.id.imgSs);
        imgPdf = view.findViewById(R.id.imgPdf);

        imgSs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgSs.setVisibility(View.GONE);

                // Take a screenshot of the current screen
                View rootView = view.getRootView();
                rootView.setDrawingCacheEnabled(true);
                Bitmap screenshot = Bitmap.createBitmap(rootView.getDrawingCache());
                rootView.setDrawingCacheEnabled(false);

                File directory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                String fileName = "screenshot_" + System.currentTimeMillis() + ".png";
                File filePath = new File(directory, fileName);

                try {
                    FileOutputStream outputStream = new FileOutputStream(filePath);
                    screenshot.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Tools.showCustomToast(getContext(), "Screenshot saved: " + filePath, view.findViewById(R.id.customToastLayout), getLayoutInflater());
                dismiss();
                imgSs.setVisibility(View.VISIBLE);
                imgPdf.setVisibility(View.VISIBLE);
            }
        });

        imgPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfDocument pdfDocument = new PdfDocument();

                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();

                PdfDocument.Page page = pdfDocument.startPage(pageInfo);

                Canvas canvas = page.getCanvas();

                canvas.drawText("Booking Receipt", 40, 50, new Paint());
                canvas.drawText("Name: " + txtName.getText().toString(), 40, 70, new Paint());
                canvas.drawText("Location: " + txtLocation.getText().toString(), 40, 90, new Paint());
                canvas.drawText("Price: " + txtPrice.getText().toString(), 40, 110, new Paint());
                canvas.drawText("Date: " + txtSelectedDate.getText().toString(), 40, 130, new Paint());
                canvas.drawText("Time: " + txtTimeSlot.getText().toString(), 40, 150, new Paint());
                canvas.drawText("Payable Amount: " + txtFinalPrice.getText().toString(), 40, 170, new Paint());

                pdfDocument.finishPage(page);

                File directory = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                String fileName = "booking_receipt_" + System.currentTimeMillis() + ".pdf";
                File filePath = new File(directory, fileName);

                try {
                    FileOutputStream outputStream = new FileOutputStream(filePath);
                    pdfDocument.writeTo(outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pdfDocument.close();

                Tools.showCustomToast(getContext(), "PDF saved: " + filePath, view.findViewById(R.id.customToastLayout), getLayoutInflater());
                dismiss();
            }
        });

        return view;
    }
}