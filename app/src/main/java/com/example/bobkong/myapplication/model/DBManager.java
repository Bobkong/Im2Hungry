package com.example.bobkong.myapplication.model;

import android.content.Context;

import com.litesuits.orm.LiteOrm;

/**
 * Created by bobkong on 2018/6/9.
 */

public class DBManager {
    private static String DB_NAME = "im2hungry";
    private static LiteOrm mManager;

    public DBManager(){

    }

    public static LiteOrm getInstance(Context context){
        if (mManager == null){
            mManager = LiteOrm.newSingleInstance(context.getApplicationContext(),DB_NAME);
        }
        return mManager;
    }
}
