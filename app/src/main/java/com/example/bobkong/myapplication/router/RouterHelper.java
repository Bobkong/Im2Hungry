package com.example.bobkong.myapplication.router;

import android.content.Context;
import android.content.Intent;

import com.example.bobkong.myapplication.net.LocationService;
import com.example.bobkong.myapplication.net.model.User;
import com.example.bobkong.myapplication.ui.DeveloperActivity;
import com.example.bobkong.myapplication.ui.LocationActivity;
import com.example.bobkong.myapplication.ui.LocationSeeActivity;
import com.example.bobkong.myapplication.ui.LoginActivity;
import com.example.bobkong.myapplication.ui.MyFavorActivity;
import com.example.bobkong.myapplication.ui.MyPostActivity;
import com.example.bobkong.myapplication.ui.PostActivity;
import com.example.bobkong.myapplication.ui.PostListActivity;
import com.example.bobkong.myapplication.widgets.DeveloperAdapter;

/**
 * Created by bobkong on 2018/6/7.
 */

public class RouterHelper {

    public static void IntentToPostActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PostActivity.class);
        context.startActivity(intent);
    }

    public static void IntentToLocationActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, LocationActivity.class);
        context.startActivity(intent);
    }

    public static void IntentToPostListActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PostListActivity.class);
        context.startActivity(intent);
    }

    public static void IntentToLoginAcitivty(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void IntentToMyPostActivity(Context context,int type,User user) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MyPostActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("user",user);
        context.startActivity(intent);
    }

    public static void IntentToLocationSeeActivity(Context context,double lat,double lng) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, LocationSeeActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lng",lng);
        context.startActivity(intent);
    }

    public static void IntentToDeveloperActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, DeveloperActivity.class);
        context.startActivity(intent);
    }

}
