package com.example.meetease.homeScreen;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PreviousMeetingAdapter extends RecyclerView.Adapter<PreviousMeetingAdapter.MeetingViewHolder> {


    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MeetingViewHolder extends RecyclerView.ViewHolder {
        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
