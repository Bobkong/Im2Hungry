package com.example.bobkong.myapplication.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.model.PostDataManager;
import com.example.bobkong.myapplication.model.PostInfo;
import com.example.bobkong.myapplication.net.PostService;
import com.example.bobkong.myapplication.net.auth.UserService;
import com.example.bobkong.myapplication.widgets.PhotoAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Bob on 2018/6/15.
 */

public class PhotoFragment extends Fragment {

    private static final String LOG_TAG = "PhotoFragment";
    private List<PostInfo> mData = new ArrayList<>();
    private GridView mPhotoGridView;
    private int mType;
    private PhotoAdapter mAdapter;
    private String mUserId;

    public static PhotoFragment newInstance(int type,String userId){
        PhotoFragment fragment = new PhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("userId",userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt("type", MyPostActivity.MY_POST);
        mUserId = getArguments().getString("userId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPhotoGridView = view.findViewById(R.id.photo_grid);
        initData();
    }

    private void initData() {

        if (mType == MyPostActivity.MY_POST){
            PostService.getPostService().getPostOfUser(mUserId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::setData, Throwable::printStackTrace);
            //mData = PostDataManager.getInstance(getActivity()).getPostDataList();
        }else if (mType == MyPostActivity.MY_FAVOR){
            PostService.getPostService().getFavoritePosts()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::setData, Throwable::printStackTrace);
            //mData = PostDataManager.getInstance(getActivity()).getPostDataList();
        }
         mAdapter = new PhotoAdapter(getActivity(), mData);
        mPhotoGridView.setAdapter(mAdapter);

        mPhotoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapter.getItem(position) != null){
                    getActivity().startActivity(new Intent(getActivity(), PostDetailActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("postInfo", mAdapter.getItem(position)));
                }

            }
        });

    }

    private void setData(List<PostInfo> list) {
        Log.d(LOG_TAG, "mType = " + mType + ", list = " + list);
        mData = list;
        mAdapter.setData(list);
    }

}
