package com.code.fastframe.utils;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.code.fastframe.FastFrame;

public class StroageUtils {

    private static final String cacheDir = "cache";

    private static File privateFile;

    private static File protectFile;

    private static File publicFile;

    /**
     * 应用私有存储（内置存储）
     * 路径是:/data/data/< package name >/files/… 不需要权限
     * context.getCacheDir()
     */
    public static File getPrivatePath() {
        if (privateFile == null) {
            privateFile = FastFrame.mApplicationContext.getFilesDir();
        }
        return privateFile;
    }

    /**
     * 应用扩展存储（SD卡）
     * 路径是: SDCard/Android/data/应用包名/files//… >19需要动态申请权限
     * context.getExternalCacheDir()
     */
    public static File getProtectPath() {
        if (protectFile == null) {
            protectFile = FastFrame.mApplicationContext.getExternalFilesDir(cacheDir);
        }
        if (protectFile != null && !protectFile.exists()) {
            protectFile.mkdirs();
        }
        return protectFile;
    }

    /**
     * 应用扩展存储（SD卡）
     * 路径是: SDCard/Android/data/应用包名/files//… >19需要动态申请权限
     * context.getExternalCacheDir()
     */
    public static File getPublicPath() {
        if (publicFile == null) {
            publicFile = Environment.getExternalStorageDirectory();
        }
        if (publicFile == null) {
            publicFile = new File(getProtectPath() + File.separator + AppUtils.getPackageName(FastFrame.mApplicationContext));
        }
        if (publicFile != null && !publicFile.exists()) {
            publicFile.mkdirs();
        }
        return publicFile;
    }
}
