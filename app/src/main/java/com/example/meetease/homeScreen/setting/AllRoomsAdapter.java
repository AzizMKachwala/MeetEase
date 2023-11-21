package com.example.meetease.homeScreen.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.meetease.R;
import com.example.meetease.dataModel.RoomDetailDataModel;
import com.example.meetease.dataModel.RoomDetailList;

import java.util.ArrayList;
import java.util.List;

public class AllRoomsAdapter extends RecyclerView.Adapter<AllRoomsAdapter.AllRoomsDataViewHolder> {

    ArrayList<RoomDetailList> roomDetailLists;
    Context context;

    public AllRoomsAdapter(ArrayList<RoomDetailList> roomDetailLists, Context context) {
        this.roomDetailLists = roomDetailLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AllRoomsDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.all_rooms_item, parent, false);
        return new AllRoomsDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllRoomsDataViewHolder holder, int position) {
        holder.txtName.setText(roomDetailLists.get(position).getRoom_name());
        holder.txtLocation.setText(roomDetailLists.get(position).getLocation());
        Glide
                .with(context)
                .load(roomDetailLists.get(position).getRoom_img())
                .into(holder.imgRoom);
    }

    @Override
    public int getItemCount() {
        return roomDetailLists.size();
    }

    public static class AllRoomsDataViewHolder extends RecyclerView.ViewHolder {

        TextView txtLocation, txtName;
        ImageView imgRoom;

        public AllRoomsDataViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            imgRoom = itemView.findViewById(R.id.imgRoom);

        }
    }
}
