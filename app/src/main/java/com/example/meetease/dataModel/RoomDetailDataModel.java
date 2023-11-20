package com.example.meetease.dataModel;

public class RoomDetailDataModel {
    String roomId;
    String roomName;
    String RoomImage;
    String location;
    String upComingStatus;
    String favoriteRoom;
    int roomRating;
    int roomPrice;
    String message;
    String status;

    public RoomDetailDataModel(String roomId, String roomName, String roomImage, String location, String upComingStatus, String favoriteRoom, int roomRating, int roomPrice, String message, String status) {
        this.roomId = roomId;
        this.roomName = roomName;
        RoomImage = roomImage;
        this.location = location;
        this.upComingStatus = upComingStatus;
        this.favoriteRoom = favoriteRoom;
        this.roomRating = roomRating;
        this.roomPrice = roomPrice;
        this.message = message;
        this.status = status;
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
}
