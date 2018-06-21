package com.example.bobkong.myapplication.net.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bob on 2018/6/17.
 */

public class Food {

    @SerializedName("menu_id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("cal")
    private int mCal;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getCal() {
        return mCal;
    }

    public void setCal(int cal) {
        mCal = cal;
    }

    @Override
    public String toString() {
        return "Food{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mCal=" + mCal +
                '}';
    }
}
