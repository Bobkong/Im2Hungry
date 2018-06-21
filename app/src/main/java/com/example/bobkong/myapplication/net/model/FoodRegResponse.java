package com.example.bobkong.myapplication.net.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bob on 2018/6/17.
 */

public class FoodRegResponse extends Response {

    @SerializedName("data")
    private FoodRegResponseData mData;

    public FoodRegResponseData getData() {
        return mData;
    }


    public Food getFood(){
        if(mData == null)
            return null;
        return mData.getFood();
    }

    @Override
    public String toString() {
        return "FoodRegResponse{" +
                "mData=" + mData +
                '}';
    }

    public void setData(FoodRegResponseData data) {
        mData = data;
    }
}
