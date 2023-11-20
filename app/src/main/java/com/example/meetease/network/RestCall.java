package com.example.meetease.network;


import com.example.meetease.dataModel.AddUserDataModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

public interface RestCall {
    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<AddUserDataModel> AddCategory(
            @Field("tag") String tag,
            @Field("user_id") String user_id,
            @Field("category_name") String category_name);


}
