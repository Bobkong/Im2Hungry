package com.example.bobkong.myapplication.ui;

import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.model.PostDataManager;
import com.example.bobkong.myapplication.model.PostInfo;
import com.example.bobkong.myapplication.net.LocationService;
import com.example.bobkong.myapplication.net.PostService;
import com.example.bobkong.myapplication.net.auth.UserService;
import com.example.bobkong.myapplication.router.RouterHelper;
import com.example.bobkong.myapplication.tools.BitmapCompressTools;
import com.example.bobkong.myapplication.widgets.PostAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import rx.android.schedulers.AndroidSchedulers;

public class PostListActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private ListView mVerticalViewPager;
    private PostAdapter mAdapter;

    private static final String LOG_TAG = PostListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolbar = findViewById(R.id.toolbar);

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

        UserService.getInstance().me()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    UserService.getInstance().setUser(user);
                    Log.d(LOG_TAG, "user_id: " + user.getUserId() + "user_name" + user.getUserName());
                    Glide.with(this)
                            .load(user.getUserImg())
                            .into((ImageView) findViewById(R.id.user_image));
                    ((TextView) findViewById(R.id.user_name)).setText(user.getUserName());
                }, Throwable::printStackTrace);

        initDrwerLayout();


    }

    private void initDrwerLayout() {
        mDrawerLayout = findViewById(R.id.id_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        findViewById(R.id.ll_user_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterHelper.IntentToMyPostActivity(PostListActivity.this, MyPostActivity.MY_POST, UserService.getInstance().getUser());
            }
        });

        findViewById(R.id.my_favor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterHelper.IntentToMyPostActivity(PostListActivity.this, MyPostActivity.MY_FAVOR, UserService.getInstance().getUser());
            }
        });

        findViewById(R.id.introduce_producer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterHelper.IntentToDeveloperActivity(PostListActivity.this);
            }
        });

        findViewById(R.id.log_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void updateDrwaerLayout() {
        if (mToolbar == null) {
            return;
        }
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
    }


    private void refreshList() {
        PostService.getPostService().queryPost(LocationService.getInstance().getLat(), LocationService.getInstance().getLng(), 2000)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> mAdapter.setDatas(list), Throwable::printStackTrace);
    }

    @Subscribe
    public void refreshAdapter(PostInfo postInfo) {
        refreshList();
        new Handler().postDelayed(() -> refreshList(), 5000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDrwaerLayout();
        refreshList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawers();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (PostDataManager.getInstance(this) != null) {
            PostDataManager.getInstance(this).destroy();
        }
        EventBus.getDefault().unregister(this);
    }
}
