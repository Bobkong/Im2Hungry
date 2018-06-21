package com.example.bobkong.myapplication.net.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bob on 2018/6/17.
 */

public class FoodRegResponseData {

    @SerializedName("food")
    private Food mFood;

    @SerializedName("img_url")
    private String mImageUrl;

    public Food getFood() {
        return mFood;
    }

    public void setFood(Food food) {
        mFood = food;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "FoodRegResponseData{" +
                "mFood=" + mFood +
                ", mImageUrl='" + mImageUrl + '\'' +
                '}';
    }
}
