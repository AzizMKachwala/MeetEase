package com.example.meetease.homeScreen.previousMeeting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetease.R;
import com.example.meetease.homeScreen.createReservation.CreateReservationAdapter;

import java.util.ArrayList;
import java.util.List;

public class PreviousMeetingAdapter extends RecyclerView.Adapter<PreviousMeetingAdapter.MeetingViewHolder> {


    List<PreviousMeetingDataModel> dataModelList,searchList;
    Context context;

    public PreviousMeetingAdapter(List<PreviousMeetingDataModel> dataModelList, Context context) {
        this.dataModelList = dataModelList;
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
            List<PreviousMeetingDataModel> filterList = new ArrayList<>();
            for (PreviousMeetingDataModel single : dataModelList){
//                if (single.getName().toLowerCase().contains(charString.toLowerCase())){
//                    filterList.add(single);
//                    flag = 1;
//                }
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
        View view = layoutInflater.inflate(R.layout.activity_login,parent,false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    class MeetingViewHolder extends RecyclerView.ViewHolder {
        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}