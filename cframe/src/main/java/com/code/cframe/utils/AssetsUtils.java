package com.code.cframe.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by dengshaomin on 2017/12/6.
 */

public class AssetsUtils {
    /*
     * 从Assets中读取文件
     */
    public static String readFile(Context context, String fileName) {
        String result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                result += line;
            }
            inputReader.close();
            bufReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /*
     * 从Assets中读取图片
     */
    public static  Bitmap readBitmap(Context context,String fileName) {
        Bitmap image = null;
        try {
            AssetManager am = context.getResources().getAssets();
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;


    }

}
