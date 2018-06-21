package com.example.bobkong.myapplication.net.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bob on 2018/6/15.
 */

public class Response {
    @SerializedName("success")
    private boolean success;

    @SerializedName("err")
    private Error mError;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Error getError() {
        return mError;
    }

    public void setError(Error error) {
        mError = error;
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", mError=" + mError +
                '}';
    }
}
