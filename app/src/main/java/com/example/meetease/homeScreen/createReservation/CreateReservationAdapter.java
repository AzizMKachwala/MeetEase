package com.example.meetease.homeScreen.createReservation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.dataModel.RoomDetailList;
import com.example.meetease.dataModel.RoomDetailListNoUpcoming;

import java.util.ArrayList;
import java.util.List;

public class CreateReservationAdapter extends RecyclerView.Adapter<CreateReservationAdapter.CreateReservationViewHolder> {

    List<RoomDetailListNoUpcoming> dataModelList, searchList;
    Context context;
    String checkFavourite = "0";

    CreateReservationAdapterDataClick createReservationAdapterDataClick;

    public interface CreateReservationAdapterDataClick {
        void bookDataClick(RoomDetailListNoUpcoming createReservationDataModel);
    }

    public void updateData(List<RoomDetailListNoUpcoming> dataModelList){
        this.dataModelList = dataModelList;
        this.searchList = dataModelList;
        notifyDataSetChanged();
    }
    public void setUpInterFace(CreateReservationAdapterDataClick createReservationAdapterDataClick) {
        this.createReservationAdapterDataClick = createReservationAdapterDataClick;
    }

    public CreateReservationAdapter(List<RoomDetailListNoUpcoming> dataModelList, Context context) {
        this.dataModelList = dataModelList;
        this.searchList = dataModelList;
        this.context = context;
    }

    void search(CharSequence charSequence, TextView textView, RecyclerView recyclerView) {

        String charString = charSequence.toString().trim();
        if (charString.isEmpty()) {
            searchList = dataModelList;
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        } else {
            int flag = 0;
            List<RoomDetailListNoUpcoming> filterList = new ArrayList<>();
            for (RoomDetailListNoUpcoming single : dataModelList) {
                if (single.getRoom_name().toLowerCase().contains(charString.toLowerCase())) {
                    filterList.add(single);
                    flag = 1;
                }
            }
            if (flag == 1) {
                searchList = filterList;
                recyclerView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CreateReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CreateReservationViewHolder(Tools.bindXML(R.layout.meeting_rooms_item,parent));
    }

    @Override
    public void onBindViewHolder(@NonNull CreateReservationViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtName.setText(searchList.get(position).getRoom_name());
        holder.txtLocation.setText(searchList.get(position).getLocation());
        holder.txtPrice.setText(searchList.get(position).getPrice() + VariableBag.CURRENCY);
        Glide
                .with(context)
                .load(searchList.get(position).getRoom_img())
                .into(holder.imgRoom);
        holder.ratingBar.setRating(Float.parseFloat(searchList.get(position).getRating()));
        holder.btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createReservationAdapterDataClick.bookDataClick(searchList.get(position));
            }
        });

        checkFavourite = "0";
        if (checkFavourite.equals("0")) {
            holder.imgFavourite.setImageResource(R.drawable.baseline_favorite_border_24);
        } else {
            holder.imgFavourite.setImageResource(R.drawable.baseline_favourite_24);
        }

        holder.imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFavourite.equals("1")) {
                    holder.imgFavourite.setImageResource(R.drawable.baseline_favorite_border_24);
                    checkFavourite = "0";
                } else {
                    holder.imgFavourite.setImageResource(R.drawable.baseline_favourite_24);
                    checkFavourite = "1";
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    static class CreateReservationViewHolder extends RecyclerView.ViewHolder {
        Button btnBookNow;
        ImageView imgRoom, imgFavourite;
        TextView txtName, txtLocation, txtPrice;
        RatingBar ratingBar;

        public CreateReservationViewHolder(@NonNull View itemView) {
            super(itemView);

            btnBookNow = itemView.findViewById(R.id.btnBookNow);
            imgRoom = itemView.findViewById(R.id.imgRooms);
            imgFavourite = itemView.findViewById(R.id.imgFavourite);
            txtName = itemView.findViewById(R.id.txtName);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
