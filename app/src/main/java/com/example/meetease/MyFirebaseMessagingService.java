package com.example.meetease;

import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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

            if (true) {

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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "123")
                .setSmallIcon(R.drawable.ic_search)
                .setContentTitle(title + "123")
                .setContentText(body + " " + body1 + "hello")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        notificationManager.notify(1, builder.build());
    }
}

