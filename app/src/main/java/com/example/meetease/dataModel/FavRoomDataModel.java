package com.example.meetease.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavRoomDataModel {
    @SerializedName("message")
    @Expose
    String message;
    @SerializedName("Bookinglist")
    @Expose
    List<FavRoomListDataModel> favRoomListDataModelList;
    @SerializedName("status")
    @Expose
    String status;

    public FavRoomDataModel(String message, List<FavRoomListDataModel> favRoomListDataModelList, String status) {
        this.message = message;
        this.favRoomListDataModelList = favRoomListDataModelList;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FavRoomListDataModel> getFavRoomListDataModelList() {
        return favRoomListDataModelList;
    }

    public void setFavRoomListDataModelList(List<FavRoomListDataModel> favRoomListDataModelList) {
        this.favRoomListDataModelList = favRoomListDataModelList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
