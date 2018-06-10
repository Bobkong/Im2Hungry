package com.example.bobkong.myapplication.ui;

import android.graphics.Bitmap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.model.PostDataManager;
import com.example.bobkong.myapplication.model.PostInfo;
import com.example.bobkong.myapplication.router.RouterHelper;
import com.example.bobkong.myapplication.tools.BitmapCompressTools;
import com.example.bobkong.myapplication.widgets.PostAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class PostListActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private ListView mVerticalViewPager;
    private PostAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolbar = findViewById(R.id.toolbar);
        initDrwerLayout();
        mAdapter = new PostAdapter(this);
        mVerticalViewPager = findViewById(R.id.post_list);
        mVerticalViewPager.setAdapter(mAdapter);
        EventBus.getDefault().register(this);
        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterHelper.IntentToPostActivity(PostListActivity.this);
            }
        });
    }

    private void initDrwerLayout() {
        mDrawerLayout = findViewById(R.id.id_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.app_name,R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        findViewById(R.id.ll_user_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterHelper.IntentToLoginAcitivty(PostListActivity.this);
            }
        });

        findViewById(R.id.my_favor).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RouterHelper.IntentToMyFavorActivity(PostListActivity.this);
            }
        });
    }

    public void updateDrwaerLayout(){
        if (mToolbar == null){
            return;
        }
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
    }

    @Subscribe
    public void refreshAdapter(PostInfo postInfo){
        postInfo.setPostImage(BitmapCompressTools.scaleBitmap(postInfo.getPostImage(),mVerticalViewPager.getWidth()));
        PostDataManager.getInstance(this).addPostData(postInfo);
        mAdapter.refreshList(PostListActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDrwaerLayout();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDrawerLayout != null){
            mDrawerLayout.closeDrawers();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (PostDataManager.getInstance(this) != null){
            PostDataManager.getInstance(this).destroy();
        }
        EventBus.getDefault().unregister( this );
    }
}
