package com.example.bobkong.myapplication.net;

import com.example.bobkong.myapplication.net.api.FoodApi;
import com.example.bobkong.myapplication.net.model.FoodRegResponse;
import com.example.bobkong.myapplication.net.model.FoodResponse;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Bob on 2018/6/17.
 */

public class FoodService {

    private static FoodService sFoodService = new FoodService();

    public static FoodService getFoodService() {
        return sFoodService;
    }


    public Observable<FoodResponse> getFoodByName(String name) {
        return NetService.getInstance().getRetrofit()
                .create(FoodApi.class)
                .getFoodByName(name)
                .subscribeOn(Schedulers.io());
    }

    public Observable<FoodRegResponse> regFood(File img) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), img);
        MultipartBody.Part body = MultipartBody.Part.createFormData("img", img.getName(), requestFile);
        return NetService.getInstance().getRetrofit()
                .create(FoodApi.class)
                .regFood(body)
                .subscribeOn(Schedulers.io());
    }


}
