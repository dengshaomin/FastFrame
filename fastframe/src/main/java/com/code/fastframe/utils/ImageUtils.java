package com.code.fastframe.utils;

import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by dengshaomin on 2017/11/7.
 */

public class ImageUtils {

    private static ImageUtils imageLoader = null;

    public static ImageUtils getInstance() {
        if (imageLoader == null) {
            synchronized (ImageUtils.class) {
                if (imageLoader == null) {
                    imageLoader = new ImageUtils();
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
