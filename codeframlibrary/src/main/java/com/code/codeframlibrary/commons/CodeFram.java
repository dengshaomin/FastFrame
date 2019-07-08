package com.code.codeframlibrary.commons;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import com.code.codeframlibrary.commons.retrofit.RetrofitHttpUtil;
import com.code.codeframlibrary.commons.utils.CLog;
import com.code.codeframlibrary.commons.utils.CSPUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

import okhttp3.OkHttpClient;

/**
 * Created by dengshaomin on 2017/12/6.
 */

public class CodeFram {

    private Context mContext;

    private static CodeFram codeFram;

    public static CodeFram getInstance() {
        if (codeFram == null) {
            synchronized (CodeFram.class) {
                if (codeFram == null) {
                    codeFram = new CodeFram();
                }
            }
        }
        return codeFram;
    }

    public void init(Context context) {
        mContext = context;
        CLog.init(mContext);
        initFresco();
    }

    private void initFresco() {
        boolean enableDownSample = Build.VERSION.SDK_INT != Build.VERSION_CODES.KITKAT;
        ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return scanNumber + 2;
            }

            public QualityInfo getQualityInfo(int scanNumber) {
                boolean isGoodEnough = (scanNumber >= 5);
                return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
            }
        };
        Set<RequestListener> listeners = new HashSet<>();
        listeners.add(new RequestLoggingListener());

        ImagePipelineConfig imagePiplineConfig = OkHttpImagePipelineConfigFactory
                .newBuilder(mContext, new OkHttpClient())
                .setRequestListeners(listeners)
                .setBitmapsConfig(Bitmap.Config.ARGB_8888)
                .setProgressiveJpegConfig(pjpegConfig)
                .setDownsampleEnabled(enableDownSample)
                .build();

        Fresco.initialize(mContext, imagePiplineConfig);
    }

    public static void onDestory(Context context) {
        CSPUtils.getInstance(context).commit();
    }

}
