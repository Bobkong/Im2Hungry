package com.example.bobkong.myapplication.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.service.notification.StatusBarNotification;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.router.RouterHelper;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by bobkong on 2018/6/9.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().setAttributes(params);

        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper())
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RouterHelper.IntentToLoginAcitivty(SplashActivity.this);
                    }
                },4000L);

        initView();
    }

    public void initView(){
        StatusBarCompat.translucentStatusBar(this,true);

        ImageView background = findViewById(R.id.background);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.anim_splash_show);
        background.startAnimation(anim);
    }
}
