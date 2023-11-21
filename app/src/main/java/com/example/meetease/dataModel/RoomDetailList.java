package com.example.meetease.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomDetailList {
    @SerializedName("room_d_id")
    @Expose
    String room_d_id;
    @SerializedName("room_name")
    @Expose
    String room_name;
    @SerializedName("price")
    @Expose
    String price;
    @SerializedName("location")
    @Expose
    String location;
    @SerializedName("room_img")
    @Expose
    String room_img;
    @SerializedName("rating")
    @Expose
    String rating;
    @SerializedName("upcoming_status")
    @Expose
    String upcoming_status;
    @SerializedName("favorite_room")
    @Expose
    String favorite_room;

    public RoomDetailList(String room_d_id, String room_name, String price, String location, String room_img, String rating, String upcoming_status, String favorite_room) {
        this.room_d_id = room_d_id;
        this.room_name = room_name;
        this.price = price;
        this.location = location;
        this.room_img = room_img;
        this.rating = rating;
        this.upcoming_status = upcoming_status;
        this.favorite_room = favorite_room;
    }

    public String getRoom_d_id() {
        return room_d_id;
    }

    public void setRoom_d_id(String room_d_id) {
        this.room_d_id = room_d_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoom_img() {
        return room_img;
    }

    public void setRoom_img(String room_img) {
        this.room_img = room_img;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUpcoming_status() {
        return upcoming_status;
    }

    public void setUpcoming_status(String upcoming_status) {
        this.upcoming_status = upcoming_status;
    }

    public String getFavorite_room() {
        return favorite_room;
    }

    public void setFavorite_room(String favorite_room) {
        this.favorite_room = favorite_room;
    }
}
