package com.code.fastframe;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Build;

import com.code.fastframe.net.NetworkStatusReceiver;
import com.code.fastframe.utils.SharedPreferencesUtils;
import com.code.rxretrofitlibrary.http.HttpUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

import leakcanary.LeakCanary;
import okhttp3.OkHttpClient;

/**
 * Created by dengshaomin on 2017/12/6.
 */

public class FastFrame {

    public static Context mApplicationContext;

    private static volatile FastFrame fastFrame;

    private NetworkStatusReceiver mNetworkReceiver;

    public static FastFrame getInstance() {
        if (fastFrame == null) {
            synchronized (FastFrame.class) {
                if (fastFrame == null) {
                    fastFrame = new FastFrame();
                }
            }
        }
        return fastFrame;
    }

    public synchronized void init(Context context) {
        if (mApplicationContext != null) {
            return;
        }
        mApplicationContext = context.getApplicationContext();
        HttpUtils.init(context);
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
                .newBuilder(mApplicationContext, new OkHttpClient())
                .setRequestListeners(listeners)
                .setBitmapsConfig(Bitmap.Config.ARGB_8888)
                .setProgressiveJpegConfig(pjpegConfig)
                .setDownsampleEnabled(enableDownSample)
                .build();

        Fresco.initialize(mApplicationContext, imagePiplineConfig);
    }

    private void registerNetStatusReceiver() {
        if (mNetworkReceiver == null) {
            mNetworkReceiver = new NetworkStatusReceiver();
            mApplicationContext.registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    private void unRegisterNetStatusReceiver() {
        if (mNetworkReceiver != null) {
            mNetworkReceiver = new NetworkStatusReceiver();
            mApplicationContext.unregisterReceiver(mNetworkReceiver);
        }
    }

    public void onDestory() {
        SharedPreferencesUtils.getInstance().commit();
        unRegisterNetStatusReceiver();
    }
}
