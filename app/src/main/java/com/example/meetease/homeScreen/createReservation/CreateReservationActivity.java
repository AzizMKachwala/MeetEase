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
import com.example.meetease.dataModel.RoomDetailListNoUpcoming;
import com.example.meetease.dataModel.RoomDetailListNoUpcomingDataModel;
import com.example.meetease.network.RestCall;
import com.example.meetease.network.RestClient;
import com.example.meetease.network.UserResponse;

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
    List<RoomDetailListNoUpcoming> apiList = new ArrayList<>();

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

        AvailableRoomDetails();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AvailableRoomDetails();
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
                            List<RoomDetailListNoUpcoming> list = ratingFilter(priceFilter(cityFilter(apiList, city), Price), Rating);
                            createReservationAdapter.updateData(list);
                        } else if (!city.isEmpty() && !Price.isEmpty() && Rating.isEmpty()) {
                            List<RoomDetailListNoUpcoming> list = priceFilter(cityFilter(apiList, city), Price);
                            createReservationAdapter.updateData(list);
                        } else if (!city.isEmpty() && Price.isEmpty() && !Rating.isEmpty()) {
                            List<RoomDetailListNoUpcoming> list = ratingFilter(cityFilter(apiList, city), Rating);
                            createReservationAdapter.updateData(list);
                        } else if (city.isEmpty() && !Price.isEmpty() && !Rating.isEmpty()) {
                            List<RoomDetailListNoUpcoming> list = ratingFilter(priceFilter(apiList, Price), Rating);
                            createReservationAdapter.updateData(list);
                        } else if (!city.isEmpty() && Price.isEmpty() && Rating.isEmpty()) {
                            List<RoomDetailListNoUpcoming> list = cityFilter(apiList, city);
                            createReservationAdapter.updateData(list);
                        } else if (city.isEmpty() && !Price.isEmpty() && Rating.isEmpty()) {
                            List<RoomDetailListNoUpcoming> list = priceFilter(apiList, Price);
                            createReservationAdapter.updateData(list);
                        } else if (city.isEmpty() && Price.isEmpty() && !Rating.isEmpty()) {
                            List<RoomDetailListNoUpcoming> list = ratingFilter(apiList, Rating);
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


    List<RoomDetailListNoUpcoming> cityFilter(List<RoomDetailListNoUpcoming> list, String city) {
        List<RoomDetailListNoUpcoming> filteredListCity = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLocation().equals(city)) {
                filteredListCity.add(list.get(i));
            }
        }
        return filteredListCity;
    }

    List<RoomDetailListNoUpcoming> priceFilter(List<RoomDetailListNoUpcoming> list, String Price) {
        List<RoomDetailListNoUpcoming> filteredListPrice = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (Integer.parseInt(list.get(i).getPrice()) <= Integer.parseInt(Price)) {
                filteredListPrice.add(list.get(i));
            }
        }
        return filteredListPrice;
    }

    List<RoomDetailListNoUpcoming> ratingFilter(List<RoomDetailListNoUpcoming> list, String Rating) {
        List<RoomDetailListNoUpcoming> filteredListRating = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRating().equals(Rating)) {
                filteredListRating.add(list.get(i));
            }
        }
        return filteredListRating;
    }


    private void AvailableRoomDetails() {
        tools.showLoading();
        restCall.AvailableRoomDetails("UnbookedRoom", year + "-" + month + "-" + day, startHour + ":"+ startMinute, endHour + ":"+ endMinute)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RoomDetailListNoUpcomingDataModel>() {
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
                                tvNoData.setVisibility(View.VISIBLE);
                            }
                        });
                    }

                    @Override
                    public void onNext(RoomDetailListNoUpcomingDataModel roomDetailListNoUpcomingDataModel) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                apiList = roomDetailListNoUpcomingDataModel.getRoomDetailListNoUpcoming();

                                if (roomDetailListNoUpcomingDataModel.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)
                                        && roomDetailListNoUpcomingDataModel.getRoomDetailListNoUpcoming() != null
                                        && roomDetailListNoUpcomingDataModel.getRoomDetailListNoUpcoming().size() >0) {

                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CreateReservationActivity.this);
                                    recyclerViewMeetingRooms.setLayoutManager(layoutManager);
                                    createReservationAdapter = new CreateReservationAdapter(apiList,CreateReservationActivity.this);
                                    recyclerViewMeetingRooms.setAdapter(createReservationAdapter);
                                    createReservationAdapter.setUpInterFace(new CreateReservationAdapter.CreateReservationAdapterDataClick() {
                                        @Override
                                        public void bookDataClick(RoomDetailListNoUpcoming createReservationDataModel) {
                                            Intent intent = new Intent(CreateReservationActivity.this, DetailsActivity.class);
                                            intent.putExtra("roomName", createReservationDataModel.getRoom_name());
                                            intent.putExtra("roomPrice", createReservationDataModel.getPrice());
                                            intent.putExtra("roomLocation", createReservationDataModel.getLocation());
                                            intent.putExtra("roomRating", createReservationDataModel.getRating());
                                            intent.putExtra("roomImage", createReservationDataModel.getRoom_img());
                                            intent.putExtra("roomId", createReservationDataModel.getRoom_d_id());
                                            intent.putExtra("bookingDate", day + "-" + month + "-" + year);
                                            intent.putExtra("bookingStartTime", startHour + ":"+ startMinute);
                                            intent.putExtra("bookingEndTime", endHour + ":"+ endHour);
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