package com.example.meetease.homeScreen.upComingMeeting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetease.R;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.dataModel.UpComingListResponse;

import java.util.List;

public class UpComingAdapter extends RecyclerView.Adapter<UpComingAdapter.UpComingViewHolder> {

    List<UpComingListResponse> upComingListResponses;
    Context context;

    public UpComingAdapter(List<UpComingListResponse> upComingListResponses, Context context) {
        this.upComingListResponses = upComingListResponses;
        this.context = context;
    }

    @NonNull
    @Override
    public UpComingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UpComingViewHolder(Tools.bindXML(R.layout.upcoming_meeting_item,parent));
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingViewHolder holder, int position) {

        holder.txtName.setText(upComingListResponses.get(position).getRoom_name());
        holder.txtPrice.setText(upComingListResponses.get(position).getPrice());
        holder.txtLocation.setText(upComingListResponses.get(position).getLocation());
        holder.txtDate.setText(upComingListResponses.get(position).getBooking_date());
        holder.txtTime.setText(upComingListResponses.get(position).getStart_time()+" - "+upComingListResponses.get(position).getEnd_time());
    }

    @Override
    public int getItemCount() {
        return upComingListResponses.size();
    }

    class UpComingViewHolder extends RecyclerView.ViewHolder {
        Button btnBookNow;
        ImageView imgRoom, imgFavourite;
        TextView txtName, txtLocation, txtPrice,txtDate,txtTime;
        public UpComingViewHolder(@NonNull View itemView) {
            super(itemView);
            btnBookNow = itemView.findViewById(R.id.btnBookNow);
            imgRoom = itemView.findViewById(R.id.imgRooms);
            imgFavourite = itemView.findViewById(R.id.imgFavourite);
            txtName = itemView.findViewById(R.id.txtName);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}
