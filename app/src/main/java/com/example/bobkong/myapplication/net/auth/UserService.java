package com.example.bobkong.myapplication.net.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.bobkong.myapplication.net.NetService;
import com.example.bobkong.myapplication.net.api.AuthApi;
import com.example.bobkong.myapplication.net.api.FoodApi;
import com.example.bobkong.myapplication.net.api.PostApi;
import com.example.bobkong.myapplication.net.api.UserApi;
import com.example.bobkong.myapplication.net.model.Response;
import com.example.bobkong.myapplication.net.model.User;
import com.google.gson.Gson;


import org.json.JSONObject;

import okhttp3.MultipartBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bobkong on 2018/6/10.
 */

public class UserService {

    private static UserService sInstance;
    private static final Gson GSON = new Gson();

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    private User mUser = new User();

    public static void initInstance(Context context) {
        if (sInstance != null)
            return;
        sInstance = new UserService(context);
    }

    private String mToken;
    private String mUserId;
    private long mExpires;
    private long mLoginTime;

    public UserService(Context context) {
    }

    public static UserService getInstance() {
        return sInstance;
    }

    public boolean shouldLogin() {
        return mUserId == null || mToken == null ||
                System.currentTimeMillis() - mLoginTime >= mExpires;
    }

    public Observable<Response> login(String userId, String accessToken,
                                      long expires) {

        return NetService.getInstance().getRetrofit()
                .create(AuthApi.class)
                .auth(userId, accessToken, expires)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(response -> {
                    if (response.isSuccess()) {
                        save(userId, accessToken, expires);
                    }
                });
    }

    private void save(String userId, String accessToken, long expires) {
        mToken = accessToken;
        mUserId = userId;
        mExpires = expires;
        mLoginTime = System.currentTimeMillis();
    }

    public Observable<User> me() {
        return NetService.getInstance().getRetrofit()
                .create(UserApi.class)
                .me()
                .subscribeOn(Schedulers.io());
    }


}
