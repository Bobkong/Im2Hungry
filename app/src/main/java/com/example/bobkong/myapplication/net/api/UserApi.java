package com.example.bobkong.myapplication.net.api;

import com.example.bobkong.myapplication.net.model.User;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Bob on 2018/6/15.
 */

public interface UserApi {

    @GET("/users/me")
    Observable<User> me();

}
