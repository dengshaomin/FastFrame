package com.code.codeframlibrary.commons.utils;

import java.io.File;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.code.codeframlibrary.commons.ciface.IGlideBitmapCallBack;
import com.code.codeframlibrary.commons.ciface.IGlideUrlCallBack;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by dengshaomin on 2017/11/7.
 */

public class CImageUtils {

    private static CImageUtils imageLoader = null;

    public static CImageUtils getInstance() {
        if (imageLoader == null) {
            synchronized (CImageUtils.class) {
                if (imageLoader == null) {
                    imageLoader = new CImageUtils();
                }
            }
        }
        return imageLoader;
    }

    /**
     * 使用当前context当 activity 或fragment生命周期结束时,glide会停止加载
     */
    public void loadImage(SimpleDraweeView imageView, String url) {
        if (url == null) {
            return;
        }
        Uri uri = Uri.parse(url);
        /*ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)//允许渐进加载
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(true)// 允许加载gif图
                .build();
        imageView.setController(controller);*/
        imageView.setImageURI(uri);
    }
}
