package com.example.meetease.homeScreen.createReservation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetease.R;

import java.util.ArrayList;
import java.util.List;

public class CreateReservationAdapter extends RecyclerView.Adapter<CreateReservationAdapter.CreateReservationViewHolder> {

    List<CreateReservationDataModel> dataModelList,searchList;
    Context context;

    CreateReservationAdapterDataClick createReservationAdapterDataClick;
    public interface CreateReservationAdapterDataClick{
        void bookDataClick(CreateReservationDataModel createReservationDataModel);
    }
    public void setUpInterFace(CreateReservationAdapterDataClick createReservationAdapterDataClick){
        this.createReservationAdapterDataClick = createReservationAdapterDataClick;
    }
    public CreateReservationAdapter(List<CreateReservationDataModel> dataModelList, Context context) {
        this.dataModelList = dataModelList;
        this.context = context;
    }

    void search(CharSequence charSequence, TextView textView, RecyclerView recyclerView){

        String charString = charSequence.toString().trim();
        if (charString.isEmpty()){
            searchList = dataModelList;
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
        else {
            int flag = 0;
            List<CreateReservationDataModel> filterList = new ArrayList<>();
            for (CreateReservationDataModel single : dataModelList){
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
    public CreateReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_login,parent,false);
        return new CreateReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateReservationViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createReservationAdapterDataClick.bookDataClick(searchList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class CreateReservationViewHolder extends RecyclerView.ViewHolder {
        Button btnBook;
        public CreateReservationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
