package com.example.meetease.homeScreen.setting;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetease.R;
import com.example.meetease.appUtils.Tools;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationDataViewHolder> {

    @NonNull
    @Override
    public NotificationDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationDataViewHolder(Tools.bindXML(R.layout.notification_item,parent)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationDataViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class NotificationDataViewHolder extends RecyclerView.ViewHolder{

        public NotificationDataViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
