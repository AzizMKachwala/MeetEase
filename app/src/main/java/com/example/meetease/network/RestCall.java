package com.example.meetease.network;


import com.example.meetease.dataModel.LoginDataModel;
import com.example.meetease.dataModel.RoomDetailDataModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Single;

public interface RestCall {

    @FormUrlEncoded
    @POST("Controller/UserController.php")
    Single<LoginDataModel> LoginUser(
            @Field("tag") String tag,
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("Controller/UserController.php")
    Single<UserResponse> AddUser(
            @Field("tag") String tag,
            @Field("full_name") String full_name,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("Controller/UserController.php")
    Single<RoomDetailDataModel> RoomDetails(
            @Field("tag") String tag);

    @Multipart
    @POST("Controller/UserController.php.php")
    Single<UserResponse> EditUser(
            @Part("tag") RequestBody tag,
            @Part("user_id") RequestBody user_id,
            @Part("full_name") RequestBody full_name,
            @Part("mobile") RequestBody mobile,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part MultipartBody.Part profile_photo1);
}
