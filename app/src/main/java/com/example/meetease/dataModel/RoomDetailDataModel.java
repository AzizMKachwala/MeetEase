package com.example.meetease.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomDetailDataModel {

    @SerializedName("roomDetailList")
    @Expose
    List<RoomDetailList> roomDetailList;
    @SerializedName("message")
    @Expose
    String message;
    @SerializedName("status")
    @Expose
    String status;

    public RoomDetailDataModel(List<RoomDetailList> roomDetailList, String message, String status) {
        this.roomDetailList = roomDetailList;
        this.message = message;
        this.status = status;
    }

    public List<RoomDetailList> getRoomDetailList() {
        return roomDetailList;
    }

    public void setRoomDetailList(List<RoomDetailList> roomDetailList) {
        this.roomDetailList = roomDetailList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class RoomDetailList{
        @SerializedName("roomId")
        @Expose
        String roomId;
        @SerializedName("roomName")
        @Expose
        String roomName;
        @SerializedName("RoomImage")
        @Expose
        String RoomImage;
        @SerializedName("location")
        @Expose
        String location;
        @SerializedName("upComingStatus")
        @Expose
        String upComingStatus;
        @SerializedName("favoriteRoom")
        @Expose
        String favoriteRoom;
        @SerializedName("roomRating")
        @Expose
        int roomRating;
        @SerializedName("roomPrice")
        @Expose
        int roomPrice;

        public RoomDetailList(String roomId, String roomName, String roomImage, String location, String upComingStatus, String favoriteRoom, int roomRating, int roomPrice) {
            this.roomId = roomId;
            this.roomName = roomName;
            RoomImage = roomImage;
            this.location = location;
            this.upComingStatus = upComingStatus;
            this.favoriteRoom = favoriteRoom;
            this.roomRating = roomRating;
            this.roomPrice = roomPrice;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public String getRoomImage() {
            return RoomImage;
        }

        public void setRoomImage(String roomImage) {
            RoomImage = roomImage;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getUpComingStatus() {
            return upComingStatus;
        }

        public void setUpComingStatus(String upComingStatus) {
            this.upComingStatus = upComingStatus;
        }

        public String getFavoriteRoom() {
            return favoriteRoom;
        }

        public void setFavoriteRoom(String favoriteRoom) {
            this.favoriteRoom = favoriteRoom;
        }

        public int getRoomRating() {
            return roomRating;
        }

        public void setRoomRating(int roomRating) {
            this.roomRating = roomRating;
        }

        public int getRoomPrice() {
            return roomPrice;
        }

        public void setRoomPrice(int roomPrice) {
            this.roomPrice = roomPrice;
        }
    }
}
