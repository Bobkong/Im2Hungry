package com.example.bobkong.myapplication.net;

import com.example.bobkong.myapplication.net.api.FavoriteApi;
import com.example.bobkong.myapplication.net.model.Response;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Bob on 2018/6/17.
 */

public class FavoriteService {
    private static FavoriteService sFavoriteService = new FavoriteService();
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public static FavoriteService getFavoriteService() {
        return sFavoriteService;
    }


    public Observable<Response> favor(String post_id, boolean favored) {
        return NetService.getInstance().getRetrofit()
                .create(FavoriteApi.class)
                .favor(post_id, favored)
                .subscribeOn(Schedulers.from(mExecutor));
    }
}
