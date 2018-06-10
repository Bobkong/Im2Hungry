package com.example.bobkong.myapplication.widgets;

import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.model.PostInfo;
import com.jaren.lib.view.LikeView;

/**
 * Created by bobkong on 2018/6/8.
 */

public class PostViewHolder {
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

    public PostViewHolder(View view) {
        userImage = view.findViewById(R.id.user_image);
        userName = view.findViewById(R.id.user_name);
        description = view.findViewById(R.id.description);
        foodName = view.findViewById(R.id.food_name);
        cal = view.findViewById(R.id.cal);
        postImage = view.findViewById(R.id.post_image);
        locationName = view.findViewById(R.id.loc_name);
        distance = view.findViewById(R.id.distance);
        favorNum = view.findViewById(R.id.favor_num);
        postTime = view.findViewById(R.id.post_time);

        favor_button = view.findViewById(R.id.favor);

        favor_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favor_button.isChecked()){
                    favor_button.setChecked(false);
                    favor_button.toggle();
                }else {
                    favor_button.setChecked(true);
                    favor_button.toggle();
                }
            }
        });
    }

    public void PaintView(PostInfo postInfo) {
        if (postInfo == null){
            return;
        }
        userImage.setImageBitmap(postInfo.getUserImage());
        userName.setText(postInfo.getUserName());
        description.setText(postInfo.getDescription());
        postImage.setImageBitmap(postInfo.getPostImage());
        locationName.setText(postInfo.getLocName());
        distance.setText(getDistance());
        foodName.setText(postInfo.getFoodName());
        cal.setText(postInfo.getCal());
        favorNum.setText(postInfo.getFavorNum() + "");
        postTime.setText(postInfo.getPostTime());
    }

    //TODO:后续需要根据经纬度计算出距离，目前暂且返回固定的“111m”
    private String getDistance() {
        return "111m";
    }

}