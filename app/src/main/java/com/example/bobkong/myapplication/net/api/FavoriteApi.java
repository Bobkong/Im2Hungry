package com.example.bobkong.myapplication.net.api;

import com.example.bobkong.myapplication.net.model.Response;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Bob on 2018/6/17.
 */

public interface FavoriteApi {

    @POST("/favorites/{post_id}")
    @FormUrlEncoded
    Observable<Response> favor(@Path("post_id") String postId, @Field("favored") boolean favored);

}
