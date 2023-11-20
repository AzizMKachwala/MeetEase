package com.example.meetease.network;


import com.example.meetease.dataModel.LoginDataModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

public interface RestCall {

    @FormUrlEncoded
    @POST("UserController.php")
    Single<LoginDataModel> LoginUser(
            @Field("tag") String tag,
            @Field("email") String email,
            @Field("password") String password);

}
