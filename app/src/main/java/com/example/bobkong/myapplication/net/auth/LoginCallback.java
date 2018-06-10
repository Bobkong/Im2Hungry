package com.example.bobkong.myapplication.net.auth;

/**
 * Created by bobkong on 2018/6/10.
 */

public interface LoginCallback {
    void onAuthError(int ret,String reqMsg);
    void onAuthSuccess(String openId, String token, String expires);
    void onAuthCancel();

}
