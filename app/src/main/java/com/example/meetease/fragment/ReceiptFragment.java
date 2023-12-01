package com.example.meetease.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    ImageView imgPdf, imgCancel;

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

        txtName.setText(getArguments().getString("roomName"));
        txtLocation.setText(getArguments().getString("roomLocation"));
        txtPrice.setText(getArguments().getString("roomPrice"));
        txtSelectedDate.setText(getArguments().getString("selectedDate"));
        txtTimeSlot.setText(getArguments().getString("startTime") + " - " + getArguments().getString("endTime"));

        imgPdf = view.findViewById(R.id.imgPdf);
        imgCancel = view.findViewById(R.id.imgCancel);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        imgPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfDocument pdfDocument = new PdfDocument();

                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();

                PdfDocument.Page page = pdfDocument.startPage(pageInfo);

                Canvas canvas = page.getCanvas();

                Paint appNamePaint = new Paint();
                appNamePaint.setTextSize(20);
                appNamePaint.setColor(Color.BLACK);
                canvas.drawText("MeetEase", 20, 50, appNamePaint);

                Paint titlePaint = new Paint();
                titlePaint.setTextSize(16);
                titlePaint.setColor(Color.BLACK);

                Paint textPaint = new Paint();
                textPaint.setTextSize(12);
                textPaint.setColor(Color.BLACK);

                int startY = 100;

                canvas.drawText("Booking Receipt", 20, startY, titlePaint);
                startY += 20;

                drawText(canvas, "Name:", txtName.getText().toString(), 20, startY, textPaint);
                drawText(canvas, "Location:", txtLocation.getText().toString(), 20, startY += 20, textPaint);
                drawText(canvas, "Price:", txtPrice.getText().toString(), 20, startY += 20, textPaint);
                drawText(canvas, "Date:", txtSelectedDate.getText().toString(), 20, startY += 20, textPaint);
                drawText(canvas, "Time:", txtTimeSlot.getText().toString(), 20, startY += 20, textPaint);

                // Draw a line
                startY += 10;
                canvas.drawLine(20, startY, 280, startY, textPaint);

                // Draw final price
                drawText(canvas, "Payable Amount:", txtFinalPrice.getText().toString(), 20, startY += 20, textPaint);

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

    private void drawText(Canvas canvas, String label, String value, float x, float y, Paint paint) {
        canvas.drawText(label, x, y, paint);
        canvas.drawText(value, x + 150, y, paint); // Adjust the X position for the value
    }
}