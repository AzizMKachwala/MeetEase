package com.example.meetease.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginDataModel {

    @SerializedName("user_id")
    @Expose
    String user_id;
    @SerializedName("full_name")
    @Expose
    String full_name;
    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("mobile")
    @Expose
    String mobile;
    @SerializedName("message")
    @Expose
    String message;
    @SerializedName("status")
    @Expose
    String status;

    public LoginDataModel(String user_id, String full_name, String email, String mobile, String message, String status) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.email = email;
        this.mobile = mobile;
        this.message = message;
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
