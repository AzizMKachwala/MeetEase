package com.example.meetease;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetease.activity.homeScreen.mainScreen.UpComingMeetingActivity;
import com.example.meetease.activity.homeScreen.mainScreen.create.DetailsActivity;
import com.example.meetease.activity.homeScreen.settings.AvailableRoomsActivity;
import com.example.meetease.adapter.AllRoomsAdapter;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.dataModel.RoomDetailDataModel;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    RestCall restCall;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        FirebaseAnalytics.getInstance(this);
        Log.d("my payload1", "Message data payload: " + remoteMessage.getData());

        if (remoteMessage.getData().size() > 0) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            String roomId = remoteMessage.getData().get("roomId");
            showNotification(title, body,roomId);
        }
    }

    private void showNotification(String title, String body,String roomId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(MyFirebaseMessagingService.this);
        }
        Intent resultIntent = new Intent(MyFirebaseMessagingService.this, DetailsActivity.class);
        if (roomId!=null){
            resultIntent.putExtra(VariableBag.roomId,roomId);
        }
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

    @Override
    public void onMessageSent(@NonNull String msgId) {
        super.onMessageSent(msgId);
        Log.i("my mess", "onMessageSent: "+msgId);
    }
}

