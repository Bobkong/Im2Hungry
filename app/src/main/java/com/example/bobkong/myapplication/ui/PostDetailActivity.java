package com.example.bobkong.myapplication.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.model.PostInfo;
import com.example.bobkong.myapplication.net.FavoriteService;
import com.example.bobkong.myapplication.net.LocationService;
import com.example.bobkong.myapplication.router.RouterHelper;
import com.example.bobkong.myapplication.tools.CalculateDistance;
import com.example.bobkong.myapplication.tools.FormatCurrentData;
import com.jaren.lib.view.LikeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Bob on 2018/6/15.
 */

public class PostDetailActivity extends AppCompatActivity {

    private ImageView userImage;
    private TextView userName;
    private TextView description;
    private ImageView postImage;
    private TextView locationName;
    private TextView distance;
    private TextView foodName;
    private TextView cal;
    private TextView favorNum;
    private TextView postTime;
    private LikeView favor_button;

    private static final String LOG_TAG = PostDetailActivity.class.getSimpleName();
    private PostInfo postInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetail);
        postInfo = (PostInfo) getIntent().getSerializableExtra("postInfo");
        initView();
    }

    private void initView() {
        userImage = findViewById(R.id.user_image);
        userName = findViewById(R.id.user_name);
        description = findViewById(R.id.description);
        foodName = findViewById(R.id.food_name);
        cal = findViewById(R.id.cal);
        postImage = findViewById(R.id.post_image);
        locationName = findViewById(R.id.loc_name);
        distance = findViewById(R.id.distance);
        favorNum = findViewById(R.id.favor_num);
        postTime = findViewById(R.id.post_time);
        favor_button = findViewById(R.id.favor);

        if (postInfo == null) {
            return;
        }
        Glide.with(this).load(Uri.parse(postInfo.getUserImage())).into(userImage);
        userName.setText(postInfo.getUserName());
        description.setText(postInfo.getDescription());
        Glide.with(this).load(Uri.parse(postInfo.getPostImageUrl())).into(postImage);
        locationName.setText(postInfo.getLocName());
        distance.setText(getDistance(postInfo));
        foodName.setText(postInfo.getFoodName());
        cal.setText(postInfo.getCal() + "cal/kg");
        favorNum.setText(postInfo.getFavorNum() + "");
        postTime.setText(FormatCurrentData.getTimeRange(postInfo.getPostTime()));

        findViewById(R.id.back).setOnClickListener(v -> finish());

        if (postInfo.isFavored()) {
            favor_button.setChecked(true);
        } else {
            favor_button.setChecked(false);
        }

        locationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterHelper.IntentToLocationSeeActivity(PostDetailActivity.this,postInfo.getLocLat(),postInfo.getLocLng());
            }
        });

        favor_button.setOnClickListener(v -> {
            if (!favor_button.isChecked()) {
                FavoriteService.getFavoriteService().favor(postInfo.getPostId(), false)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            postInfo.setFavorNum(postInfo.getFavorNum() - 1);
                            Log.d(LOG_TAG, response.toString());
                            if (response.isSuccess()) {
                                favorNum.setText(String.valueOf(postInfo.getFavorNum()));
                            }
                        }, Throwable::printStackTrace);
            } else {
                FavoriteService.getFavoriteService().favor(postInfo.getPostId(), true)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            postInfo.setFavorNum(postInfo.getFavorNum() + 1);
                            Log.d(LOG_TAG, response.toString());
                            if (response.isSuccess()) {
                                favorNum.setText(String.valueOf(postInfo.getFavorNum()));
                            }
                        }, Throwable::printStackTrace);
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterHelper.IntentToMyPostActivity(PostDetailActivity.this, MyPostActivity.MY_POST, postInfo.getUser());
            }
        });
    }

    private String getDistance(PostInfo postInfo) {

        double distance = CalculateDistance.algorithm(postInfo.getLocLng(), postInfo.getLocLat(), LocationService.getInstance().getLng(), LocationService.getInstance().getLat());
        if (distance >= 1000) {
            return (String.valueOf(distance / 1000) + "km");
        }
        return String.valueOf(distance) + "m";
    }

    @Subscribe
    public void getPostInfo(PostInfo postInfo) {
        if (postInfo != null) {
            this.postInfo = postInfo;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
