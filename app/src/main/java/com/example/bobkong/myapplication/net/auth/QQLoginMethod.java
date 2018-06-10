package com.example.bobkong.myapplication.net.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

public class QQLoginMethod{
    private final String TAG = QQLoginMethod.class.getSimpleName();

    private Context mContext;

    private String mAppid = "222222";
    private Tencent mTencent;
    private UserInfo mUserInfo;
    private QQLoginListener mQQLoginListener;
    private QQUserInfoListener mQQUserInfoListener;
    private LoginCallback mLoginCallback;

    public QQLoginMethod(Context ctx) {

        if (ctx != null) {
            mContext = ctx.getApplicationContext();
            mTencent = Tencent.createInstance(mAppid, mContext);
            mQQLoginListener = new QQLoginListener();
            mQQUserInfoListener = new QQUserInfoListener();
        }
    }

    public void setmLoginCallback(LoginCallback mLoginCallback) {
        this.mLoginCallback = mLoginCallback;
    }

    private void doLoginComplete(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token)
                    && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
                mUserInfo = new UserInfo(mContext, mTencent.getQQToken());
                mUserInfo.getUserInfo(mQQUserInfoListener);
                if (mLoginCallback != null) {
                    mLoginCallback.onAuthSuccess(openId, token, expires);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    public void getUserInfo()
    {
        mTencent.requestAsync(Constants.GRAPH_SIMPLE_USER_INFO, null,
                Constants.HTTP_GET, new BaseApiListener("get_simple_userinfo", false),
                null);
    }*/

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "lukexi onActivityResult code = " + requestCode);
        if (requestCode != Constants.REQUEST_LOGIN) {
            return;
        }
        Tencent.onActivityResultData(requestCode, resultCode, data, mQQLoginListener);
    }

    private class QQUserInfoListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            JSONObject json = (JSONObject) o;
            if (json.has("figureurl")) {
                try {
                    String fingure_url = json.getString("figureurl_qq_2");
                    Log.d(TAG, "lukexi test fingure_url = " + fingure_url);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        @Override
        public void onError(UiError uiError) {
            Log.d(TAG, "lukexi user info error: " + uiError.errorMessage);
        }

        @Override
        public void onCancel() {
            Log.d(TAG, "lukexi user info cancel");
        }
    }

    private class QQLoginListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Log.d(TAG, "lukexi login complete!");
            if (null == response) {
                if (mLoginCallback != null) {
                    mLoginCallback.onAuthError(0, "response nothing");
                }
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (jsonResponse.length() == 0) {
                if (mLoginCallback != null) {
                    mLoginCallback.onAuthError(0, "json format error");
                }
                return;
            }
            doLoginComplete((JSONObject) response);
        }

        @Override
        public void onError(UiError uiError) {
            Log.d(TAG, "lukexi login error: " + uiError.errorMessage);
            if (mLoginCallback != null) {
                mLoginCallback.onAuthError(uiError.errorCode, uiError.errorDetail);
            }
        }

        @Override
        public void onCancel() {
            Log.d(TAG, "lukexi login cancel");
            if (mLoginCallback != null) {
                mLoginCallback.onAuthCancel();
            }
        }
    }

    public int login(Activity activity, @Nullable Bundle arguments) {
        if (null == mTencent) {
            return -1;
        }
        return mTencent.login(activity, "all,", mQQLoginListener);
    }

    public int login() {
        if (null == mTencent) {
            return -1;
        }

        boolean isValid = mTencent.checkSessionValid(mAppid);
        if (!isValid) {
            return -2;
        }

        JSONObject jsonObject = mTencent.loadSession(mAppid);
        mTencent.initSessionCache(jsonObject);
        return 0;
    }

    public int logout() {
        if (null == mTencent) {
            return LoginConstans.CODE_FAIL;
        }
        mTencent.logout(mContext);
        return LoginConstans.CODE_SUCCESS;
    }

    public boolean isLogin() {
        return mTencent != null && mTencent.isSessionValid();
    }


}
