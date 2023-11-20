package com.example.meetease.network;


import com.example.meetease.dataModel.AddUserDataModel;
import com.example.meetease.dataModel.RoomDetailDataModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

public interface RestCall {
    @FormUrlEncoded
    @POST("UserController.php")
    Single<UserResponse> AddUser(
            @Field("tag") String tag,
            @Field("full_name") String full_name,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("UserController.php")
    Single<RoomDetailDataModel> RoomDetails(
            @Field("tag") String tag);

}
