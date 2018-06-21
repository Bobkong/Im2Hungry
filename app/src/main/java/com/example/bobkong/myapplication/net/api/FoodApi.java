package com.example.bobkong.myapplication.net.api;

import com.example.bobkong.myapplication.net.model.FoodRegResponse;
import com.example.bobkong.myapplication.net.model.FoodResponse;
import com.example.bobkong.myapplication.net.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Bob on 2018/6/16.
 */

public interface FoodApi {

    @GET("/foods/{food_name}")
    Observable<FoodResponse> getFoodByName(@Path("food_name") String food_name);

    @POST("/foodreg")
    @Multipart
    Observable<FoodRegResponse> regFood(@Part MultipartBody.Part img);
}
