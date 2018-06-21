package com.example.bobkong.myapplication.net;

import com.example.bobkong.myapplication.model.PostInfo;
import com.example.bobkong.myapplication.net.api.PostApi;
import com.example.bobkong.myapplication.net.model.Response;

import java.io.File;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import rx.Observable;

import rx.schedulers.Schedulers;

/**
 * Created by Bob on 2018/6/17.
 */

public class PostService {

    private static PostService sPostService = new PostService();

    public static PostService getPostService() {
        return sPostService;
    }

    public Observable<Response> post(@Field("loc_lat") double loc_lat, @Field("loc_lng") double loc_lng,
                                     @Field("description") String description, @Field("loc_name") String loc_name,
                                     @Field("food_name") String food_name, @Field("food_cal") String food_cal,
                                     @Field("img") File img) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), img);
        MultipartBody.Part body = MultipartBody.Part.createFormData("img", img.getName(), requestFile);
        return NetService.getInstance().getRetrofit()
                .create(PostApi.class)
                .post(field(loc_lat), field(loc_lng), field(description), field(loc_name), field(food_name), field(food_cal),
                        body)
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<PostInfo>> queryPost(double lat, double lng, double distance) {
        return NetService.getInstance().getRetrofit()
                .create(PostApi.class)
                .queryPost(lat, lng, distance, 50)
                .subscribeOn(Schedulers.io());
    }



    private RequestBody field(double value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(value));
    }


    private RequestBody field(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }

    public Observable<List<PostInfo>> getFavoritePosts() {
        return NetService.getInstance().getRetrofit()
                .create(PostApi.class)
                .getFavoritePosts(true,50)
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<PostInfo>> getPostOfUser(String userId) {
        return NetService.getInstance().getRetrofit()
                .create(PostApi.class)
                .getPostOfUser(userId,50)
                .subscribeOn(Schedulers.io());
    }
}
