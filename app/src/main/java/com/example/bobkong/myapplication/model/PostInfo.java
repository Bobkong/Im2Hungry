package com.example.bobkong.myapplication.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobkong on 2018/6/7.
 */
@Table("post_info")
public class PostInfo implements Serializable{
    @PrimaryKey(PrimaryKey.AssignType.BY_MYSELF)
    private String postId;
    private String mUserName;
    private Bitmap mUserImage;
    private String mCal;
    private String mFoodName;
    private int mLocLat;
    private int mLocLng;
    private String mLocName;
    private String mDescription;
    private String mPostTime;
    private Bitmap mPostImage;
    private int mFavorNum;

    public PostInfo(String mUserName, Bitmap mUserImage, String mCal, String mFoodName, int mLocLat, int mLocLng, String mLocName, String mDescription, String mPostTime, Bitmap mPostImage, int mFavorNum) {
        this.mUserName = mUserName;
        this.mUserImage = mUserImage;
        this.mCal = mCal;
        this.mFoodName = mFoodName;
        this.mLocLat = mLocLat;
        this.mLocLng = mLocLng;
        this.mLocName = mLocName;
        this.mDescription = mDescription;
        this.mPostTime = mPostTime;
        this.mPostImage = mPostImage;
        this.mFavorNum = mFavorNum;
    }

    public String getUserName() {
        return mUserName;
    }

    public Bitmap getUserImage() {
        return mUserImage;
    }

    public String getCal() {
        return mCal;
    }

    public String getFoodName() {
        return mFoodName;
    }

    public int getLocLat() {
        return mLocLat;
    }

    public int getLocLng() {
        return mLocLng;
    }

    public String getLocName() {
        return mLocName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPostTime() {
        return mPostTime;
    }

    public Bitmap getPostImage() {
        return mPostImage;
    }

    public int getFavorNum() {
        return mFavorNum;
    }

    public void setPostImage(Bitmap picture){
        this.mPostImage = picture;
    }
    public static void addToFavor(Context context,PostInfo postInfo){
        if (context == null || postInfo == null){
            return;
        }
        DBManager.getInstance(context).save(postInfo);
    }

    public static List<PostInfo> queryAllFavors(Context context){
        if (context == null){
            return new ArrayList<>();
        }
        return DBManager.getInstance(context)
                .query(new QueryBuilder(PostInfo.class));
    }

    public static void delete(Context context,PostInfo postInfo){
        if (context == null || postInfo == null){
            return;
        }
        DBManager.getInstance(context).delete(postInfo);
    }
}
