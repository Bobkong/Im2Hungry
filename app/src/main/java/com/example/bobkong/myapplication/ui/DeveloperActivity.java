package com.example.bobkong.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.model.PostDataManager;
import com.example.bobkong.myapplication.model.PostInfo;
import com.example.bobkong.myapplication.widgets.DeveloperAdapter;
import com.example.bobkong.myapplication.widgets.PhotoAdapter;

import java.util.List;

/**
 * Created by Bob on 2018/6/16.
 */

public class DeveloperActivity extends AppCompatActivity {
    private GridView mPhotoGridView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        mPhotoGridView = findViewById(R.id.photo_grid);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
    }

    private void initData() {


        DeveloperAdapter mAdapter;

        mAdapter = new DeveloperAdapter(this);
        mPhotoGridView.setAdapter(mAdapter);
    }
}
