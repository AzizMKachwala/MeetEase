package com.example.meetease;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.meetease.activity.homeScreen.mainScreen.UpComingMeetingActivity;
import com.example.meetease.activity.homeScreen.mainScreen.create.PaymentActivity;
import com.example.meetease.activity.homeScreen.mainScreen.create.PaymentSuccessActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        FirebaseAnalytics.getInstance(this);
        Log.d("my payload1", "Message data payload: " + remoteMessage);

        if (remoteMessage.getData().size() > 0) {


            if ( true) {

            } else {
                String title = remoteMessage.getData().get("title");
                String body = remoteMessage.getData().get("body");
                String body1 = remoteMessage.getData().get("body1");
                showNotification(title, body, body1);
                Log.d("my payload", "Message data payload: " + remoteMessage);
            }
        }
    }

    private void showNotification(String title, String body, String body1) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(MyFirebaseMessagingService.this);
        }
        Intent resultIntent = new Intent(MyFirebaseMessagingService.this, UpComingMeetingActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyFirebaseMessagingService.this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(MyFirebaseMessagingService.this, "meeting_notification")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.bg)
                .setContentIntent(resultPendingIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) MyFirebaseMessagingService.this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(Context context) {
        NotificationChannel channel = new NotificationChannel("meeting_notification", "meeting_notification", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Channel for meeting notifications");
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}

