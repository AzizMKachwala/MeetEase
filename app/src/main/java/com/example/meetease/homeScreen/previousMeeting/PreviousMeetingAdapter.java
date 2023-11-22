package com.example.meetease.homeScreen.previousMeeting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.meetease.R;
import com.example.meetease.dataModel.RoomDetailDataModel;
import com.example.meetease.dataModel.RoomDetailList;

import java.util.ArrayList;
import java.util.List;

public class PreviousMeetingAdapter extends RecyclerView.Adapter<PreviousMeetingAdapter.MeetingViewHolder> {


    List<RoomDetailList> dataModelList,searchList;
    Context context;

    public PreviousMeetingAdapter(List<RoomDetailList> dataModelList, Context context) {
        this.dataModelList = dataModelList;
        this.searchList = dataModelList;
        this.context = context;
    }

    public void search(CharSequence charSequence, TextView textView, RecyclerView recyclerView){

        String charString = charSequence.toString().trim();
        if (charString.isEmpty()){
            searchList = dataModelList;
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
        else {
            int flag = 0;
            List<RoomDetailList> filterList = new ArrayList<>();
            for (RoomDetailList single : dataModelList){
                if (single.getRoom_name().toLowerCase().contains(charString.toLowerCase())){
                    filterList.add(single);
                    flag = 1;
                }
            }
            if (flag == 1){
                searchList = filterList;
                recyclerView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
            }
            else {
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.previous_meeting_item,parent,false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        holder.txtName.setText(searchList.get(position).getRoom_name());
        holder.txtLocation.setText(searchList.get(position).getLocation());
        holder.ratingBar.setRating(Float.parseFloat(searchList.get(position).getRating()));
        Glide
                .with(context)
                .load(searchList.get(position).getRoom_img())
                .into(holder.imgRoom);
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    class MeetingViewHolder extends RecyclerView.ViewHolder {

        ImageView imgRoom;
        TextView txtName,txtLocation,txtDate,txtTime;
        RatingBar ratingBar;
        Button btnBookAgain;

        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);

            btnBookAgain = itemView.findViewById(R.id.btnBookNow);
            imgRoom = itemView.findViewById(R.id.imgRoom);
            txtName = itemView.findViewById(R.id.txtName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
