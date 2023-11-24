package com.example.meetease.homeScreen.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetease.R;
import com.example.meetease.appUtils.Tools;
import com.example.meetease.appUtils.VariableBag;
import com.example.meetease.dataModel.RoomDetailDataModel;
import com.example.meetease.dataModel.RoomDetailList;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class FavoriteRoomActivity extends AppCompatActivity {

    RecyclerView recycleFavRoom;
    EditText etvSearch;
    TextView tvNoData;
    ImageView ivClose;
    RestCall restCall;
    Tools tools;
    FavoriteRoomAdapter favoriteRoomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_room);
        recycleFavRoom = findViewById(R.id.recycleFavRoom);
        etvSearch = findViewById(R.id.etvSearch);
        tvNoData = findViewById(R.id.tvNoData);
        ivClose = findViewById(R.id.ivClose);
        ivClose.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);

        tools = new Tools(this);
        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        ivClose.setOnClickListener(view -> {
            ivClose.setVisibility(View.GONE);
            etvSearch.setText("");
        });
        etvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (favoriteRoomAdapter != null) {
                    if (!etvSearch.getText().toString().isEmpty()) {
                        ivClose.setVisibility(View.VISIBLE);
                    } else {
                        ivClose.setVisibility(View.GONE);
                    }
                    favoriteRoomAdapter.search(charSequence, tvNoData, recycleFavRoom);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    void roomDetail() {
        tools.showLoading();
        restCall.RoomDetails("getRoom")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RoomDetailDataModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Log.e("##error", e.getLocalizedMessage());
                                Toast.makeText(FavoriteRoomActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(RoomDetailDataModel roomDetailDataModel) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (roomDetailDataModel.getStatus().equals(VariableBag.SUCCESS_RESULT) && roomDetailDataModel.getRoomDetailList() != null && !roomDetailDataModel.getRoomDetailList().isEmpty()) {

                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FavoriteRoomActivity.this);
                                    recycleFavRoom.setLayoutManager(layoutManager);
                                    List<RoomDetailList> newList = new ArrayList<>();
                                    for (int i=0; i<roomDetailDataModel.getRoomDetailList().size();i++){

                                        if (roomDetailDataModel.getRoomDetailList().get(i).getFavorite_room().equals("1")){
                                            newList.add(roomDetailDataModel.getRoomDetailList().get(i));
                                        }
                                    }
                                    favoriteRoomAdapter = new FavoriteRoomAdapter(newList, FavoriteRoomActivity.this);
                                    recycleFavRoom.setAdapter(favoriteRoomAdapter);
                                }
                            }
                        });
                    }
                });
    }
}