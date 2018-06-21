package com.example.bobkong.myapplication.net.api;

import com.example.bobkong.myapplication.model.PostInfo;
import com.example.bobkong.myapplication.net.model.Response;

import java.util.List;

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

public interface PostApi {
    @Multipart
    @POST("/posts/")
    Observable<Response> post(@Part("loc_lat") RequestBody loc_lat, @Part("loc_lng") RequestBody loc_lng,
                              @Part("description") RequestBody description, @Part("loc_name") RequestBody loc_name,
                              @Part("food_name") RequestBody food_name, @Part("food_cal") RequestBody food_cal,
                              @Part MultipartBody.Part img);

    @GET("/posts")
    Observable<List<PostInfo>> queryPost(@Query("loc_lat") double loc_lat, @Query("loc_lng") double loc_lng, @Query("distance") double distance, @Query("max") int max);

    @GET("/posts")
    Observable<List<PostInfo>> getFavoritePosts(@Query("favored") boolean favored, @Query("max") int max);

    @GET("/posts")
    Observable<List<PostInfo>> getPostOfUser(@Query("post_user_id") String userId, @Query("max") int max);

    @GET("/posts/{post_id}")
    Observable<PostInfo> getPost(@Path("post_id") String postId);
}
