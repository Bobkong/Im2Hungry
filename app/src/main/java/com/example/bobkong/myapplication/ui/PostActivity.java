package com.example.bobkong.myapplication.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bobkong.myapplication.R;
import com.example.bobkong.myapplication.app.App;
import com.example.bobkong.myapplication.model.PostDataManager;
import com.example.bobkong.myapplication.model.PostInfo;
import com.example.bobkong.myapplication.tools.BitmapCompressTools;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bobkong on 2018/6/7.
 */

public class PostActivity extends AppCompatActivity{
    private File mFile;
    private static final int PERMISSION_CAMERA_CODE = 0;
    private Bitmap picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mFile = new File(Environment.getDownloadCacheDirectory(),"im2hungry.temp.png");
        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postNewData();
            }
        });

        findViewById(R.id.add_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCameraPermission();
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_CODE);
        }else{
           addImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CAMERA_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //拍照
                    addImage();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                        addImage();
                    }else{
                        Toast.makeText(this, "授权失败！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    public void addImage(){
        Intent intent = Intent.createChooser(new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/*"),getString(R.string.select_image))
                .putExtra(Intent.EXTRA_INITIAL_INTENTS,
                        new Intent[]{
                            new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                .putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(mFile))
                        });

        startActivityForResult(intent, 12345);
    }

    @Override
    public void onActivityResult(int req, int res, Intent data)
    {
        super.onActivityResult(req, res, data);
        if (res == RESULT_OK){

            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                // 这里直接decode了图片，没有判断图片大小，没有对可能出现的OOM做处理
                try {
                    picture = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // 显示图片
                setPostData(picture);
            }else {
                Log.d("mmeeeeee","nnnn");
            }
        }


    }

    private void setPostData(Bitmap picture) {
        ((ImageView)findViewById(R.id.add_image)).setImageBitmap(picture);
        ((ImageView)findViewById(R.id.add_image)).setScaleType(ImageView.ScaleType.CENTER_CROP);
        findViewById(R.id.add_image).setPadding(0,0,0,0);

        findViewById(R.id.ll_cal).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_food_name).setVisibility(View.VISIBLE);

        ((TextView)findViewById(R.id.food_name)).setText(((TextView)findViewById(R.id.food_name)).getText().toString() + "香蕉");
        ((TextView)findViewById(R.id.cal)).setText(((TextView)findViewById(R.id.cal)).getText().toString() + "498cal/100g");
    }

    private void postNewData() {
        PostInfo postInfo = new PostInfo("bobkong", BitmapFactory.decodeResource(this.getResources(), R.mipmap.userimage),"1000cal/100g","香蕉",0,0,
                "腾讯大厦",((EditText)findViewById(R.id.description)).getText().toString(),getCurrentTime(),picture,0);

        Log.d("Image", "Image: " + picture.getWidth() + "   " + picture.getHeight());

        EventBus.getDefault().post(postInfo);
        finish();
    }

    public String getCurrentTime(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date());// new Date()为获取当前系统时间
    }
}
