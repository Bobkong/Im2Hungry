package com.example.bobkong.myapplication.ui;

import android.Manifest;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
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
import com.example.bobkong.myapplication.net.FoodService;
import com.example.bobkong.myapplication.net.PostService;
import com.example.bobkong.myapplication.net.auth.UserService;
import com.example.bobkong.myapplication.router.RouterHelper;
import com.example.bobkong.myapplication.tools.BitmapCompressTools;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatCodePointException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bobkong on 2018/6/7.
 */

public class PostActivity extends AppCompatActivity {
    private static final String LOG_TAG = PostListActivity.class.getSimpleName();
    private File mFile;
    private static final int LOCATION_REQUEST_CODE = 111;
    private static final int CAMERA_REQUEST_CODE = 123;
    private static final int PERMISSION_CAMERA_CODE = 0;
    private Bitmap picture;
    private double mLat, mLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mFile = new File(getExternalCacheDir(), "im2hungry.temp.png");
        findViewById(R.id.post).setOnClickListener(view -> postNewData());

        findViewById(R.id.add_image).setOnClickListener(view -> requestCameraPermission());

        findViewById(R.id.back).setOnClickListener(v -> finish());

        findViewById(R.id.choose_location).setOnClickListener(v -> {
            Intent intent = new Intent(PostActivity.this, LocationActivity.class);
            PostActivity.this.startActivityForResult(intent, LOCATION_REQUEST_CODE);
        });

        findViewById(R.id.correct_food_name).setOnClickListener(v -> showCorrectDialog());
    }

    private void showCorrectDialog() {
        final EditText et = new EditText(this);
        et.setHint("请输入食物名称");
        new AlertDialog.Builder(this).setTitle("输入食物")
                .setView(et)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    //按下确定键后的事件
                    ((TextView) findViewById(R.id.food_name)).setText(et.getText().toString());
                    FoodService.getFoodService().getFoodByName(et.getText().toString())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(foodResp -> {
                                        Log.d(LOG_TAG, "foodResp: " + foodResp.isSuccess() + "food: " + foodResp.getFood().toString());
                                        if (foodResp.isSuccess() && foodResp.getFood() != null) {
                                            ((TextView) findViewById(R.id.cal)).setText(String.valueOf(foodResp.getFood().getCal()) + "cal/100g");
                                        } else {
                                            ((TextView) findViewById(R.id.cal)).setText("0cal/100g");
                                        }
                                    }
                                    , e -> {
                                        e.printStackTrace();
                                        ((TextView) findViewById(R.id.cal)).setText("0cal/100g");
                                    });
                }).setNegativeButton("取消", null).show();
    }

    void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_CODE);
        } else {
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
                    } else {
                        Toast.makeText(this, "授权失败！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    public void addImage() {
        Intent intent = Intent.createChooser(new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/*"), getString(R.string.select_image))
                .putExtra(Intent.EXTRA_INITIAL_INTENTS,
                        new Intent[]{
                                new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                        .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile))
                        });

        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if (res == RESULT_OK) {
            switch (req) {
                case CAMERA_REQUEST_CODE:
                    boolean shouldCompress = false;
                    Uri uri;
                    if (data == null || data.getData() == null) {
                        uri = Uri.fromFile(mFile);
                    } else {
                        uri = data.getData();
                    }
                    // 这里直接decode了图片，没有判断图片大小，没有对可能出现的OOM做处理
                    try {
                        picture = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        if (picture.getWidth() > 1080)
                            picture = BitmapCompressTools.scaleBitmap(picture, 1080);
                        setPostData(picture);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Observable.fromCallable(() -> {
                        picture.compress(Bitmap.CompressFormat.JPEG, 77, new FileOutputStream(mFile));
                        return mFile;
                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::reg, Throwable::printStackTrace);
                    // 显示图片
                    break;
                case LOCATION_REQUEST_CODE:
                    ((TextView) findViewById(R.id.loc_name)).setText(data.getStringExtra("select_location"));
                    mLat = data.getDoubleExtra("select_lat", 0);
                    mLng = data.getDoubleExtra("select_lng", 0);
            }

        }
    }

    private void reg(File file) {
        FoodService.getFoodService().regFood(mFile)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(foodResp -> {
                    Log.d(LOG_TAG, foodResp.toString());
                    if (foodResp.getFood() != null) {
                        ((TextView) findViewById(R.id.food_name)).setText(foodResp.getFood().getName());
                        ((TextView) findViewById(R.id.cal)).setText(foodResp.getFood().getCal() + "cal/100g");
                    }
                });
    }


    private void setPostData(Bitmap picture) {
        ((ImageView) findViewById(R.id.add_image)).setImageBitmap(picture);
        ((ImageView) findViewById(R.id.add_image)).setScaleType(ImageView.ScaleType.CENTER_CROP);
        findViewById(R.id.add_image).setPadding(0, 0, 0, 0);

        findViewById(R.id.ll_cal).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_food_name).setVisibility(View.VISIBLE);

        ((TextView) findViewById(R.id.food_name)).setText("识别中....");
        ((TextView) findViewById(R.id.cal)).setText("");


    }

    private void postNewData() {
        if (!verifyPost()) {
            return;
        }
        PostService.getPostService().post(mLat, mLng, ((EditText) findViewById(R.id.description)).getText().toString(), ((TextView) findViewById(R.id.loc_name)).getText().toString(),
                ((TextView) findViewById(R.id.food_name)).getText().toString(), ((TextView) findViewById(R.id.cal)).getText().toString(), mFile)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d(LOG_TAG, response.toString());
                    if (response.isSuccess()) {
                        finish();
                    }
                }, Throwable::printStackTrace);

        EventBus.getDefault().post(new PostInfo());
        finish();
    }

    public String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date());// new Date()为获取当前系统时间
    }


    private boolean verifyPost() {
        if (picture == null) {
            Toast.makeText(this, getString(R.string.verify_take_pic), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (((TextView) findViewById(R.id.loc_name)).getText().toString().equals(getString(R.string.choose_loc_hint))) {
            Toast.makeText(this, R.string.verify_choos_loc, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
