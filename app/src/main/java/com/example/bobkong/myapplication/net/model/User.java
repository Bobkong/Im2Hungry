package com.example.bobkong.myapplication.net.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bob on 2018/6/15.
 */

public class User implements Serializable{

    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("img")
    private String mUserImg;
    @SerializedName("sex")
    private int mSex;
    @SerializedName("username")
    private String mUserName;

    public User() {
    }

    public User(String userId, String userImg, int sex, String userName) {
        mUserId = userId;
        mUserImg = userImg;
        mSex = sex;
        mUserName = userName;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getUserImg() {
        return mUserImg;
    }

    public void setUserImg(String userImg) {
        mUserImg = userImg;
    }

    public int getSex() {
        return mSex;
    }

    public void setSex(int sex) {
        mSex = sex;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }
}
