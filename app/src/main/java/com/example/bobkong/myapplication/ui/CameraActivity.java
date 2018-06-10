package com.example.bobkong.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.app.App;
import com.google.android.cameraview.CameraView;

/**
 * Created by Bob on 2018/6/10.
 */

public class CameraActivity extends AppCompatActivity {
    private CameraView mCameraView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mCameraView = findViewById(R.id.camera);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraView.start();
    }

    @Override
    protected void onPause() {
        mCameraView.stop();
        super.onPause();
    }
}
