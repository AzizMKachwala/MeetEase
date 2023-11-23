package com.example.meetease.homeScreen.setting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetease.R;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.homeScreen.createReservation.CreateReservationAdapter;

public class FavoriteRoomAdapter extends RecyclerView.Adapter<FavoriteRoomAdapter.FavoriteViewHolder> {


    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteViewHolder(Tools.bindXML(R.layout.favorite_item,parent));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
