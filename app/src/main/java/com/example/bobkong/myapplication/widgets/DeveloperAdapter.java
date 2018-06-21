package com.example.bobkong.myapplication.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.model.DevoloperInfo;
import com.example.bobkong.myapplication.model.PostInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobkong on 2018/6/9.
 */

public class DeveloperAdapter extends BaseAdapter{

    private Context context;
    private List<DevoloperInfo> mData = new ArrayList<>();

    public DeveloperAdapter(Context context) {
        this.context = context;
        initData();
    }

    private void initData() {
        mData.add(new DevoloperInfo("changiliu(刘畅)","产品/leader",R.mipmap.changiliu));
        mData.add(new DevoloperInfo("chloeywwang(王怡雯)","产品",R.mipmap.chloeywwang));
        mData.add(new DevoloperInfo("bobkong(孔令爽)","终端",R.mipmap.bobkong));
        mData.add(new DevoloperInfo("binxhuang(黄艺彬)","终端",R.mipmap.binxhuang));
        mData.add(new DevoloperInfo("cedricyu(于海)","模型",R.mipmap.cedricyu));
        mData.add(new DevoloperInfo("fencefeng(冯文锋)","模型",R.mipmap.fencefeng));
        mData.add(new DevoloperInfo("scarlettliu(刘思佳)","后台",R.mipmap.scarlettliu));
        mData.add(new DevoloperInfo("sherrijiang(蒋珊)","测试",R.mipmap.sherrijiang));
        mData.add(new DevoloperInfo("anitawhchen(陈文花)","后台",R.mipmap.anitawhchen));
        mData.add(new DevoloperInfo("oreohuang(黄康)","后台",R.mipmap.oreohuang));
        mData.add(new DevoloperInfo("heatherwang(王妙涵)","设计/酱油",R.mipmap.heatherwang));
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public DevoloperInfo getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DeveloperAdapter.ViewHolder vh = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_developer,null);
            vh = new DeveloperAdapter.ViewHolder();
            vh.imageView = view.findViewById(R.id.developer_image);
            vh.name = view.findViewById(R.id.developer_name);
            vh.job = view.findViewById(R.id.developer_job);
            view.setTag(vh);
        }
        vh = (DeveloperAdapter.ViewHolder) view.getTag();
        if(mData != null && mData.size() >= 0){
            vh.imageView.setImageResource(mData.get(i).getImage());
            vh.name.setText(mData.get(i).getName());
            vh.job.setText(mData.get(i).getJob());
        }
        return view;
    }

    class ViewHolder{
        TextView name;
        TextView job;
        ImageView imageView;
    }

}
