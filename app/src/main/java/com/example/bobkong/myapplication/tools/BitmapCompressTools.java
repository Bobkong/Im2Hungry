package com.example.bobkong.myapplication.tools;

/**
 * Created by bobkong on 2018/6/10.
 */
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

public class BitmapCompressTools {
    public static Bitmap decodeSampledBitmapFromResource(Bitmap bit){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 50;
        bit.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        Bitmap compressBitmap;
        compressBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return compressBitmap;
    }

    public  static  Bitmap scaleBitmap(Bitmap origin, int width){
        int originWidth = origin.getWidth();
        int height = width * origin.getHeight() / originWidth;
        return Bitmap.createScaledBitmap(origin, width, height, false);

    }
}

