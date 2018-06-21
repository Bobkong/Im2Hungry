
package com.example.bobkong.myapplication.widgets;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.model.PostInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Bob on 2018/6/15.
 */


public class PhotoAdapter extends BaseAdapter{
    private Context context;
    private List<PostInfo> mData = new ArrayList<>();

    public PhotoAdapter(Context context,List<PostInfo> data) {
        this.context = context;
        this.mData = data;
    }

    public void setData(List<PostInfo> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public PostInfo getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.photo_item,null);
            vh = new ViewHolder();
            vh.imageView = view.findViewById(R.id.photo);
            view.setTag(vh);
        }
        vh = (ViewHolder) view.getTag();
        if(mData != null && mData.size() >= 0){
            Glide.with(view.getContext()).load(Uri.parse(mData.get(i).getPostImageUrl())).into(vh.imageView);
        }
        return view;
    }

    class ViewHolder{
        ImageView imageView;
    }
}

