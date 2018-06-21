package com.example.bobkong.myapplication.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.model.PostDataManager;
import com.example.bobkong.myapplication.net.LocationService;
import com.example.bobkong.myapplication.net.NetService;
import com.example.bobkong.myapplication.net.auth.LoginCallback;
import com.example.bobkong.myapplication.net.auth.QQLoginMethod;
import com.example.bobkong.myapplication.net.auth.UserService;
import com.example.bobkong.myapplication.router.RouterHelper;
import com.example.bobkong.myapplication.tools.CalculateDistance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

import static android.content.pm.PackageManager.PERMISSION_DENIED;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by bobkong on 2018/6/10.
 */

public class LoginActivity extends AppCompatActivity implements LoginCallback {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 23345;
    private QQLoginMethod loginMethod;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginMethod = new QQLoginMethod(this);
        findViewById(R.id.qq_logo).setOnClickListener(v -> loginMethod.login(LoginActivity.this, null));
        loginMethod.setmLoginCallback(this);
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE);
        LocationService.initInstance(this.getApplicationContext());
        NetService.initInstance(getApplicationContext());
        UserService.initInstance(getApplicationContext());
        if(!UserService.getInstance().shouldLogin()){
            RouterHelper.IntentToPostListActivity(this);
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginMethod.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAuthError(int ret, String reqMsg) {
        Log.d("QQ", "ret=" + ret + ", msg=" + reqMsg);
    }

    @Override
    public void onAuthSuccess(String openId, String token, String expires) {
        Log.d("QQ", "id=" + openId + ", token=" + token + ", exp=" + expires);
        UserService.getInstance().login(openId, token, Long.parseLong(expires))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        RouterHelper.IntentToPostListActivity(this);
                        finish();
                    } else {
                        Log.d(LOG_TAG, response.getError().toString());
                    }
                }, Throwable::printStackTrace);
    }

    @Override
    public void onAuthCancel() {

    }

    protected void checkPermission(String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] requestPermissions = getRequestPermissions(permissions);
            if (requestPermissions.length > 0) {
                requestPermissions(requestPermissions, PERMISSION_REQUEST_CODE);
            }
        } else {
            int[] grantResults = new int[permissions.length];
            Arrays.fill(grantResults, PERMISSION_GRANTED);
            onRequestPermissionsResult(PERMISSION_REQUEST_CODE, permissions, grantResults);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private String[] getRequestPermissions(String[] permissions) {
        List<String> list = new ArrayList<>();
        for (String permission : permissions) {
            if (checkSelfPermission(permission) == PERMISSION_DENIED) {
                list.add(permission);
            }
        }
        return list.toArray(new String[list.size()]);
    }

}
