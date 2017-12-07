package com.code.codeframlibrary.commons.utils;

import java.io.File;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.code.codeframlibrary.commons.CodeFram;
import com.code.codeframlibrary.commons.ciface.CIGlideBitmapCallBack;
import com.code.codeframlibrary.commons.ciface.CIGlideUrlCallBack;
import com.code.codeframlibrary.commons.glide.GlideApp;

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
    public void loadImage(Context context, ImageView imageView, String url) {
        if (imageView == null || context == null) {
            return;
        }
        GlideApp.with(context).load(url)
//              .placeholder(R.drawable.icon_back)
//              .error(R.drawable.icon_back)
                .into(imageView);
    }

    /**
     * 使用当前applictioncontext
     */
    public void loadImage(ImageView imageView, String url) {
        if (imageView == null || CodeFram.mContext == null) {
            return;
        }
        GlideApp.with(CodeFram.mContext).load(url)
//                .placeholder(R.drawable.icon_back)
//                .error(R.drawable.icon_back)
                .into(imageView);
    }

    public void getDiskUrl(Context context, String imageUrl, CIGlideUrlCallBack ciGlideUrlCallBack) {
        if (TextUtils.isEmpty(imageUrl) || context == null || ciGlideUrlCallBack == null) {
            return;
        }
        GetImageCacheTask getImageCacheTask = new GetImageCacheTask();
        getImageCacheTask.SaveImageTask(context);
        getImageCacheTask.execute(imageUrl, ciGlideUrlCallBack);
    }

    public void getDiskImage(Context context, String imageUrl, final CIGlideBitmapCallBack ciGlideUrlCallBack) {
        if (TextUtils.isEmpty(imageUrl) || context == null || ciGlideUrlCallBack == null) {
            return;
        }
        Glide.with(context).load(imageUrl).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                ciGlideUrlCallBack.callBack(((BitmapDrawable) resource).getBitmap());
            }
        });
    }

    class GetImageCacheTask extends AsyncTask<Object, Void, File> {

        private Context context;

        private CIGlideUrlCallBack mCIGlideUrlCallBack;

        public void SaveImageTask(Context context) {
            this.context = context;
        }

        @Override
        protected File doInBackground(Object... params) {
            String imgUrl = (String) params[0];
            mCIGlideUrlCallBack = (CIGlideUrlCallBack) params[1];
            try {
                return Glide.with(context)
                        .load(imgUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(File result) {
            if (result == null) {
                mCIGlideUrlCallBack.callBack(null);
            } else {
                mCIGlideUrlCallBack.callBack(result.getPath());
            }
        }
    }

}
