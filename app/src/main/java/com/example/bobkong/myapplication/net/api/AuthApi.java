package com.example.bobkong.myapplication.net.api;

import com.example.bobkong.myapplication.net.model.Response;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Bob on 2018/6/15.
 */

public interface AuthApi {

    @POST("/auth/qq")
    @FormUrlEncoded
    Observable<Response> auth(@Field("user_id") String userId, @Field("access_token") String accessToken,
                              @Field("expires") long expires);
}
