package com.example.bobkong.myapplication.router;

import android.content.Context;
import android.content.Intent;

import com.example.bobkong.myapplication.ui.LocationActivity;
import com.example.bobkong.myapplication.ui.LoginActivity;
import com.example.bobkong.myapplication.ui.MyFavorActivity;
import com.example.bobkong.myapplication.ui.MyPostActivity;
import com.example.bobkong.myapplication.ui.PostActivity;
import com.example.bobkong.myapplication.ui.PostListActivity;

/**
 * Created by bobkong on 2018/6/7.
 */

public class RouterHelper {

    public static void IntentToPostActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PostActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void IntentToLocationActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, LocationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void IntentToPostListActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PostListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void IntentToLoginAcitivty(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void IntentToMyFavorActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MyFavorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void IntentToMyPostActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MyPostActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
