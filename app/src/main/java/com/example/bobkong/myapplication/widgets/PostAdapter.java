package com.example.bobkong.myapplication.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.model.PostDataManager;
import com.example.bobkong.myapplication.model.PostInfo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by bobkong on 2018/6/9.
 */

public class PostAdapter extends BaseAdapter {

    List<PostInfo> mDatas = new CopyOnWriteArrayList<>();
    LayoutInflater mInflater;

    public PostAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mDatas = PostDataManager.getInstance(context).getPostDataList();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (position < 0 || position >= mDatas.size()){
            return null;
        }
        PostViewHolder postViewHolder;
        if (convertView != null){
           postViewHolder = (PostViewHolder)convertView.getTag();
        }else {
            convertView = mInflater.inflate(R.layout.post_item,null);
            postViewHolder = new PostViewHolder(convertView);
            convertView.setTag(postViewHolder);
        }

        postViewHolder.PaintView(mDatas.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        // 获取当前数据的hashCode
        int hashCode = mDatas.get(position).hashCode();
        return hashCode;
    }

    public void refreshList(Context context) {
        mDatas = PostDataManager.getInstance(context).getPostDataList();
        this.notifyDataSetChanged();
    }
}
