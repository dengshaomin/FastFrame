package com.code.fastframe.utils;

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
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputReader.close();
                bufReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
     * 从Assets中读取图片
     */
    public static Bitmap readBitmap(Context context, String fileName) {
        Bitmap image = null;
        InputStream is = null;
        try {
            AssetManager am = context.getResources().getAssets();
            is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;


    }

}
