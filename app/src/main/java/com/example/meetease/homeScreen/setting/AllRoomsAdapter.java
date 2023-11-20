package com.example.meetease.homeScreen.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetease.R;
import com.example.meetease.dataModel.RoomDetailDataModel;

import java.util.List;

public class AllRoomsAdapter extends RecyclerView.Adapter<AllRoomsAdapter.AllRoomsDataViewHolder> {

    List<RoomDetailDataModel.RoomDetailList> roomDetailLists;
    Context context;

    public AllRoomsAdapter(List<RoomDetailDataModel.RoomDetailList> roomDetailLists, Context context) {
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
