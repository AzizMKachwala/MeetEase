package com.example.meetease.appUtils;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.meetease.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Tools {
    Context context;
    private final Dialog dialog;

    public Tools(Context context) {
        this.context = context;
        dialog = new Dialog(context);
    }

    public static boolean isValidEmail(String str) {
        if (!TextUtils.isEmpty(str)) {
            return Patterns.EMAIL_ADDRESS.matcher(str.toLowerCase()).matches();
        }
        return false;
    }
    public static boolean isValidPassword(String password)
    {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public void showLoading() {
        try {
            if (dialog != null) {
                dialog.setContentView(R.layout.loadingdialog);
                dialog.setCancelable(false);
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopLoading() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void DisplayImage(Context context, ImageView img, String urlImg) {
        Glide.with(context)
                .load(urlImg)
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground))
                .into(img);
    }
}
