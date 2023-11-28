package com.example.meetease.homeScreen.upComingMeeting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetease.R;
import com.example.meetease.appUtils.Tools;

public class UpComingAdapter extends RecyclerView.Adapter<UpComingAdapter.UpComingViewHolder> {


    Context context;
    @NonNull
    @Override
    public UpComingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UpComingViewHolder(Tools.bindXML(R.layout.previous_meeting_item,parent));
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class UpComingViewHolder extends RecyclerView.ViewHolder {
        public UpComingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
