package com.code.codeframlibrary.commons;

import android.content.Context;

import com.code.codeframlibrary.commons.fresco.ImageLoaderConfig;
import com.code.codeframlibrary.commons.retrofit.RetrofitHttpUtil;
import com.code.codeframlibrary.commons.utils.CLog;
import com.code.codeframlibrary.commons.utils.CSPUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by dengshaomin on 2017/12/6.
 */

public class CodeFram {

    public static Context mContext;

    public static void init(Context context, String serverUrl) {
        mContext = context;
//        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
//                .setDownsampleEnabled(true)
//                .build();
//        Fresco.initialize(context, config);
        RetrofitHttpUtil.getInstance().init(serverUrl);
        CLog.init(mContext);
        Fresco.initialize(context, ImageLoaderConfig.getImagePipelineConfig(context));
    }

    public static void onDestory(Context context) {
        CSPUtils.getInstance(context).commit();
    }
}
