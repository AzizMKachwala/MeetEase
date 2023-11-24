package com.example.meetease.homeScreen.createReservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class CreateReservationActivity extends AppCompatActivity {

    RecyclerView recyclerViewMeetingRooms;
    CreateReservationAdapter createReservationAdapter;
    EditText etvSearch;
    TextView tvNoData;
    ImageView ivClose, img_filter;
    FilterFragment filterFragment;
    SwipeRefreshLayout swipeRefreshLayout;
    RestCall restCall;
    Tools tools;
    List<RoomDetailList> apiList = new ArrayList<>();

    public int year, month, day, startHour, startMinute, endHour, endMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

        recyclerViewMeetingRooms = findViewById(R.id.recyclerViewMeetingRooms);
        etvSearch = findViewById(R.id.etvSearch);
        tvNoData = findViewById(R.id.tvNoData);
        img_filter = findViewById(R.id.img_filter);
        ivClose = findViewById(R.id.ivClose);
        swipeRefreshLayout = findViewById(R.id.swipe);
        tools = new Tools(this);
        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        roomDetail();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        ivClose.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);

        ivClose.setOnClickListener(view -> {
            ivClose.setVisibility(View.GONE);
            etvSearch.setText("");
        });

        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterFragment = new FilterFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                filterFragment.show(fragmentTransaction, "#tag");
                filterFragment.setCancelable(false);

                filterFragment.setUpInterface(new FilterFragment.FilterApply() {
                    @Override
                    public void filterList(String city, String Price, String Rating) {

                        if (!city.isEmpty() && !Price.isEmpty() && !Rating.isEmpty()) {
                            List<RoomDetailList> list = ratingFilter(priceFilter(cityFilter(apiList, city), Price), Rating);
                            createReservationAdapter.updateData(list);
                        }

                        else if (!city.isEmpty() && !Price.isEmpty() && Rating.isEmpty()) {
                            List<RoomDetailList> list = priceFilter(cityFilter(apiList, city), Price);
                            createReservationAdapter.updateData(list);
                        }

                        else if (!city.isEmpty() && Price.isEmpty() && !Rating.isEmpty()) {
                            List<RoomDetailList> list = ratingFilter(cityFilter(apiList, city), Rating);
                            createReservationAdapter.updateData(list);
                        }

                        else if (city.isEmpty() && !Price.isEmpty() && !Rating.isEmpty()) {
                            List<RoomDetailList> list = ratingFilter(priceFilter(apiList, Price), Rating);
                            createReservationAdapter.updateData(list);
                        }

                        else if (!city.isEmpty() && Price.isEmpty() && Rating.isEmpty()) {
                            List<RoomDetailList> list = cityFilter(apiList, city);
                            createReservationAdapter.updateData(list);
                        }

                        else if (city.isEmpty() && !Price.isEmpty() && Rating.isEmpty()) {
                            List<RoomDetailList> list = priceFilter(apiList, Price);
                            createReservationAdapter.updateData(list);
                        }

                        else if (city.isEmpty() && Price.isEmpty() && !Rating.isEmpty()) {
                            List<RoomDetailList> list = ratingFilter(apiList, Rating);
                            createReservationAdapter.updateData(list);
                        }
                    }
                    @Override
                    public void reset() {
                        createReservationAdapter.updateData(apiList);
                    }
                });
            }
        });

        Intent intent = getIntent();
        year = Integer.parseInt(intent.getExtras().getString("year", "0"));
        month = Integer.parseInt(intent.getExtras().getString("month", "0"));
        day = Integer.parseInt(intent.getExtras().getString("day", "0"));
        startHour = Integer.parseInt(intent.getExtras().getString("startHour", "0"));
        startMinute = Integer.parseInt(intent.getExtras().getString("startMinute", "0"));
        endHour = Integer.parseInt(intent.getExtras().getString("endHour", "0"));
        endMinute = Integer.parseInt(intent.getExtras().getString("endMinute", "0"));

        etvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (createReservationAdapter != null) {
                    if (!etvSearch.getText().toString().isEmpty()) {
                        ivClose.setVisibility(View.VISIBLE);
                    } else {
                        ivClose.setVisibility(View.GONE);
                    }
                    createReservationAdapter.search(charSequence, tvNoData, recyclerViewMeetingRooms);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    List<RoomDetailList> cityFilter(List<RoomDetailList> list, String city) {
        List<RoomDetailList> filteredListCity = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLocation().equals(city)) {
                filteredListCity.add(list.get(i));
            }
        }
        return filteredListCity;
    }

    List<RoomDetailList> priceFilter(List<RoomDetailList> list, String Price) {
        List<RoomDetailList> filteredListPrice = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (Integer.parseInt(list.get(i).getPrice()) <= Integer.parseInt(Price)) {
                filteredListPrice.add(list.get(i));
            }
        }
        return filteredListPrice;
    }

    List<RoomDetailList> ratingFilter(List<RoomDetailList> list, String Rating) {
        List<RoomDetailList> filteredListRating = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRating().equals(Rating)) {
                filteredListRating.add(list.get(i));
            }
        }
        return filteredListRating;
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
                                Toast.makeText(CreateReservationActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(RoomDetailDataModel roomDetailDataModel) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                apiList = roomDetailDataModel.getRoomDetailList();
                                if (roomDetailDataModel.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CreateReservationActivity.this);
                                    recyclerViewMeetingRooms.setLayoutManager(layoutManager);
                                    List<RoomDetailList> newList = new ArrayList<>();
                                    for (int i = 0; i < roomDetailDataModel.getRoomDetailList().size(); i++) {
                                        if (roomDetailDataModel.getRoomDetailList().get(i).getUpcoming_status().equals("0")) {
                                            newList.add(roomDetailDataModel.getRoomDetailList().get(i));
                                        }
                                    }
                                    createReservationAdapter = new CreateReservationAdapter(newList, CreateReservationActivity.this);
                                    recyclerViewMeetingRooms.setAdapter(createReservationAdapter);
                                    createReservationAdapter.setUpInterFace(new CreateReservationAdapter.CreateReservationAdapterDataClick() {
                                        @Override
                                        public void bookDataClick(RoomDetailList createReservationDataModel) {
                                            Intent intent = new Intent(CreateReservationActivity.this, DetailsActivity.class);
                                            intent.putExtra("roomName", createReservationDataModel.getRoom_name());
                                            intent.putExtra("roomPrice", createReservationDataModel.getPrice());
                                            intent.putExtra("roomLocation", createReservationDataModel.getLocation());
                                            intent.putExtra("roomRating", createReservationDataModel.getRating());
                                            intent.putExtra("roomImage", createReservationDataModel.getRoom_img());
                                            intent.putExtra("roomId", createReservationDataModel.getRoom_d_id());
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
    }
}