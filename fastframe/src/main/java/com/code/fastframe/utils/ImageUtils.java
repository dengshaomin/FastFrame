package com.code.fastframe.utils;

import java.io.File;

import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.code.fastframe.ciface.IImageUtils;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

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


    public File getCachedImageOnDisk(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainFileCache().getResource(new SimpleCacheKey(url));
        return resource == null ? null : resource.getFile();
    }

    public void downLoadImage(String url, IImageUtils iImageUtils) {
        if (iImageUtils == null) {
            return;
        }
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setProgressiveRenderingEnabled(true).build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, this);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(@Nullable Bitmap bitmap) {
                if (iImageUtils != null) {
                    iImageUtils.downloadFinish(bitmap);
                }
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
                if (iImageUtils != null) {
                    iImageUtils.downloadFinish(null);
                }
            }
        }, CallerThreadExecutor.getInstance());

    }

}
