package com.example.bobkong.myapplication.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.model.PostInfo;
import com.example.bobkong.myapplication.net.auth.UserService;
import com.example.bobkong.myapplication.net.model.User;

/**
 * Created by bobkong on 2018/6/9.
 */

public class MyPostActivity extends FragmentActivity {

    private ViewPager mViewPager;
    public static final int MY_POST = 0;
    public static final int MY_FAVOR = 1;
    private User mUser;

    private FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return PhotoFragment.newInstance(MyPostActivity.MY_POST,mUser.getUserId());
                case 1:
                    return PhotoFragment.newInstance(MyPostActivity.MY_FAVOR,mUser.getUserId());
            }
            return PhotoFragment.newInstance(MyPostActivity.MY_POST,mUser.getUserId());
        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        mUser = (User) getIntent().getSerializableExtra("user");
        Glide.with(this)
                .load(mUser.getUserImg())
                .into((ImageView) findViewById(R.id.user_image));
        ((TextView) findViewById(R.id.user_name)).setText(mUser.getUserName());
        if (mUser.getSex() == 0){
            ((ImageView) findViewById(R.id.user_sex)).setImageResource(R.mipmap.ic_male);
        }else {
            ((ImageView) findViewById(R.id.user_sex)).setImageResource(R.mipmap.ic_female);
        }

        mViewPager = findViewById(R.id.viewpager);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewPager.setAdapter(adapter);
        TabLayout mTablayout = findViewById(R.id.navigation_tab);
        mTablayout.addTab(mTablayout.newTab().setText(getString(R.string.my_post)),0);
        mTablayout.addTab(mTablayout.newTab().setText(getString(R.string.my_favor)),1);
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //tab被选的时候回调
                mViewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab未被选择的时候回调
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //tab重新选择的时候回调
            }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
        if (getIntent().getIntExtra("type",0) == MY_POST){
            mViewPager.setCurrentItem(MY_POST);
        }else {
            mViewPager.setCurrentItem(MY_FAVOR);
        }
    }

}
