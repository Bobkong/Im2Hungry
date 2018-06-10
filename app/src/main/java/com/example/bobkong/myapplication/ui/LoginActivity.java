package com.example.bobkong.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.net.auth.LoginCallback;
import com.example.bobkong.myapplication.net.auth.QQLoginMethod;
import com.example.bobkong.myapplication.router.RouterHelper;

/**
 * Created by bobkong on 2018/6/10.
 */

public class LoginActivity extends AppCompatActivity implements LoginCallback {

    private QQLoginMethod loginMethod;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         loginMethod = new QQLoginMethod(this);
         findViewById(R.id.qq_logo).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //loginMethod.login(LoginActivity.this, null);
                 RouterHelper.IntentToPostListActivity(LoginActivity.this);
             }
         });
         loginMethod.setmLoginCallback(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginMethod.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAuthError(int ret, String reqMsg) {
        Log.d("QQ", "ret=" + ret +", msg=" + reqMsg);
    }

    @Override
    public void onAuthSuccess(String openId, String token, String expires) {
        Log.d("QQ", "id="+openId+", token="+token +", exp="+expires);
        RouterHelper.IntentToPostListActivity(this);
    }

    @Override
    public void onAuthCancel() {

    }
}
