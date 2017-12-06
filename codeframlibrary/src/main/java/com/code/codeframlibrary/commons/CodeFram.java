package com.code.codeframlibrary.commons;

import android.content.Context;

import com.code.codeframlibrary.commons.retrofit.RetrofitHttpUtil;
import com.code.codeframlibrary.commons.utils.CSPUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by dengshaomin on 2017/12/6.
 */

public class CodeFram {

    public static Context mContext;

    public static void init(Context context, String serverUrl) {
        mContext = context;
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(context, config);
        RetrofitHttpUtil.BASE_URL = serverUrl;
    }

    public static void init(Context context) {
        init(context, null);
    }

    public static void onDestory(Context context) {
        CSPUtils.getInstance(context).commit();
    }
}
