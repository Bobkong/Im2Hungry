package com.example.bobkong.myapplication.net.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bob on 2018/6/17.
 */

public class FoodResponse extends Response {

    @SerializedName("food")
    private Food mFood;

    public Food getFood() {
        return mFood;
    }

    public void setFood(Food food) {
        mFood = food;
    }

    @Override
    public String toString() {
        return "FoodResponse{" +
                "mFood=" + mFood +
                '}';
    }
}
