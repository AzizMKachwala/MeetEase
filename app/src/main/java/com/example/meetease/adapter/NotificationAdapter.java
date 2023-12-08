package com.example.meetease.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetease.R;
import com.example.meetease.appUtils.Tools;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationDataViewHolder> {

    @NonNull
    @Override
    public NotificationDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationDataViewHolder(Tools.bindXML(R.layout.notification_item, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationDataViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class NotificationDataViewHolder extends RecyclerView.ViewHolder {

        TextView txtNotificationName, txtNotificationDescription, txtCurrentTime;

        public NotificationDataViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNotificationName = itemView.findViewById(R.id.txtNotificationName);
            txtNotificationDescription = itemView.findViewById(R.id.txtNotificationDescription);
            txtCurrentTime = itemView.findViewById(R.id.txtCurrentTime);

        }
    }
}
