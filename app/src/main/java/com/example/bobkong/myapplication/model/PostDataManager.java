package com.example.bobkong.myapplication.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.bobkong.myapplication.R;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by bobkong on 2018/6/7.
 */

public class PostDataManager {

    private static PostDataManager mPostDataManager;
    private Context mContext;
    private List<PostInfo> mPostDataList = new CopyOnWriteArrayList<>();

    public static PostDataManager getInstance(Context context){
        if (mPostDataManager == null){
            mPostDataManager = new PostDataManager(context);
        }
        return mPostDataManager;
    }

    //Todo:接入数据源
    private PostDataManager(Context context){
        mContext = context;
        mPostDataList.add(new PostInfo("bobkong", BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.userimage),"1000cal/100g","红烧牛肉面",
                0,0,"腾讯大厦","我们的早餐竟然是牛肉面，第三天、第四天、第五天的早餐也是牛肉面……牛肉面真是好好吃啊！ 一碗热气腾腾的牛肉面上桌，我被笼罩在绕萦的热气中。这热气，有着诱人的香味，夹杂着牛肉、青菜、白面和汤料的清香。端上桌来时，翻滚的热气扑鼻而来，闻一闻没有想象中的清香，甚至还会被浓烈的香料味呛到，一向喜欢清淡口味的我甚至感觉有些窒息。",
                "11:42",BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.foodimage),120));

        mPostDataList.add(new PostInfo("黄艺彬", BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.userimage),"1000cal/100g","红烧牛肉面",
                0,0,"腾讯大厦","我们的早餐竟然是牛肉面，第三天、第四天、第五天的早餐也是牛肉面……牛肉面真是好好吃啊！ 一碗热气腾腾的牛肉面上桌，我被笼罩在绕萦的热气中。这热气，有着诱人的香味，夹杂着牛肉、青菜、白面和汤料的清香。端上桌来时，翻滚的热气扑鼻而来，闻一闻没有想象中的清香，甚至还会被浓烈的香料味呛到，一向喜欢清淡口味的我甚至感觉有些窒息。",
                "11:42",BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.foodimage),120));

        mPostDataList.add(new PostInfo("冯文锋", BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.userimage),"1000cal/100g","红烧牛肉面",
                0,0,"腾讯大厦","我们的早餐竟然是牛肉面，第三天、第四天、第五天的早餐也是牛肉面……牛肉面真是好好吃啊！ 一碗热气腾腾的牛肉面上桌，我被笼罩在绕萦的热气中。这热气，有着诱人的香味，夹杂着牛肉、青菜、白面和汤料的清香。端上桌来时，翻滚的热气扑鼻而来，闻一闻没有想象中的清香，甚至还会被浓烈的香料味呛到，一向喜欢清淡口味的我甚至感觉有些窒息。",
                "11:42",BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.foodimage),120));

        mPostDataList.add(new PostInfo("bobkong", BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.userimage),"1000cal/100g","红烧牛肉面",
                0,0,"腾讯大厦","我们的早餐竟然是牛肉面，第三天、第四天、第五天的早餐也是牛肉面……牛肉面真是好好吃啊！ 一碗热气腾腾的牛肉面上桌，我被笼罩在绕萦的热气中。这热气，有着诱人的香味，夹杂着牛肉、青菜、白面和汤料的清香。端上桌来时，翻滚的热气扑鼻而来，闻一闻没有想象中的清香，甚至还会被浓烈的香料味呛到，一向喜欢清淡口味的我甚至感觉有些窒息。",
                "11:42",BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.foodimage),120));

        mPostDataList.add(new PostInfo("bobkong", BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.userimage),"1000cal/100g","红烧牛肉面",
                0,0,"腾讯大厦","我们的早餐竟然是牛肉面，第三天、第四天、第五天的早餐也是牛肉面……牛肉面真是好好吃啊！ 一碗热气腾腾的牛肉面上桌，我被笼罩在绕萦的热气中。这热气，有着诱人的香味，夹杂着牛肉、青菜、白面和汤料的清香。端上桌来时，翻滚的热气扑鼻而来，闻一闻没有想象中的清香，甚至还会被浓烈的香料味呛到，一向喜欢清淡口味的我甚至感觉有些窒息。",
                "11:42",BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.foodimage),120));
    }

    public List<PostInfo> getPostDataList(){
        return mPostDataList;
    }

    public void addPostData(PostInfo postInfo){
        mPostDataList.add(0,postInfo);
    }

    public void destroy(){
        if (mPostDataList != null){
            mPostDataList.clear();
            mPostDataList = null;
        }
        if (mContext != null){
            mContext = null;
        }
        if (mPostDataManager != null){
            mPostDataManager = null;
        }
    }
}
