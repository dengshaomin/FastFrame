package com.code.fastframe.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.WindowManager;


public class ScreenUtils {

    private static int realWidth = 0;

    private static int realHeight = 0;

    private static int width = 0;

    private static int height = 0;

    public static int getScreenWidth(Context context) {
        boolean island = isLandscape(context);
        if (width > 0) {
            return island ? height : width;
        }
        DisplayMetrics metric = Resources.getSystem().getDisplayMetrics();
        if (metric != null) {
            width = island ? metric.heightPixels : metric.widthPixels;
            height = island ? metric.widthPixels : metric.heightPixels;
        }
        return island ? height : width;
    }

    /**
     * 获得屏幕高度
     */
    public static int getRealScreenWidth(Activity context) {
        boolean island = isLandscape(context);
        if (realWidth > 0) {
            return island ? realHeight : realWidth;
        }
/**
 * getRealMetrics - 屏幕的原始尺寸，即包含状态栏。
 * version >= 4.2.2
 */
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            realWidth = island ? metrics.heightPixels : metrics.widthPixels;
            realHeight = island ? metrics.widthPixels : metrics.heightPixels;
        } else {
            try {
                WindowManager wm = (WindowManager) context
                        .getSystemService(Context.WINDOW_SERVICE);
                DisplayMetrics outMetrics = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(outMetrics);
                realWidth = island ? outMetrics.heightPixels : outMetrics.widthPixels;
                realHeight = island ? outMetrics.widthPixels : outMetrics.heightPixels;
            } catch (Exception e) {

            }
        }
        return island ? realHeight : realWidth;
    }

    public static int getScreenHeight(Context context) {
        boolean island = isLandscape(context);
        if (height > 0) {
            return island ? width : height;
        }
        DisplayMetrics metric = Resources.getSystem().getDisplayMetrics();
        if (metric != null) {
            width = island ? metric.heightPixels : metric.widthPixels;
            height = island ? metric.widthPixels : metric.heightPixels;
        }
        return island ? width : height;
    }

    public static int getRealScreenHeight(Activity context) {
        boolean island = isLandscape(context);
        if (realHeight > 0) {
            return island ? realWidth : realHeight;
        }
        /**
         * getRealMetrics - 屏幕的原始尺寸，即包含状态栏。
         * version >= 4.2.2
         */
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            realWidth = island ? metrics.heightPixels : metrics.widthPixels;
            realHeight = island ? metrics.widthPixels : metrics.heightPixels;
        } else {
            try {
                WindowManager wm = (WindowManager) context
                        .getSystemService(Context.WINDOW_SERVICE);
                DisplayMetrics outMetrics = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(outMetrics);
                realWidth = island ? outMetrics.heightPixels : outMetrics.widthPixels;
                realHeight = island ? outMetrics.widthPixels : outMetrics.heightPixels;
            } catch (Exception e) {

            }
        }
        return island ? realWidth : realHeight;
    }

    public static void rotateScreen(Context mContext, int orientation) {
        if (mContext == null) {
            return;
        }
        if (orientation == Surface.ROTATION_0) {
            //竖屏正方向
            setScreenOrientationUnderProtect((Activity) mContext, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (orientation == Surface.ROTATION_90) {
            setScreenOrientationUnderProtect((Activity) mContext, ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        } else if (orientation == Surface.ROTATION_180) {
            //竖屏反方向
            setScreenOrientationUnderProtect((Activity) mContext, ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        } else if (orientation == Surface.ROTATION_270) {
            ScreenUtils
                    .setScreenOrientationUnderProtect((Activity) mContext, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    /**
     * 对升级targetSdkVersion到26及以上之后，Android O(8.0)以上activity透明主题设置屏幕方向造成的系统bug进行保护，屏幕旋转可能不生效，注意android 8.1 release 1
     * (refer to https://android.googlesource.com/platform/frameworks/base/+log/refs/tags/android-8.1.0_r1/core/java/android/app/Activity.java)
     * 版之后的系统代码已不会出现该问题（refer to https://android.googlesource.com/platform/frameworks/base/+/d4ecffae67fa0dea03c581ca26f76b87a14be763%5E%21/#F0）
     *
     * 引起问题的代码：https://github.com/aosp-mirror/platform_frameworks_base/commit/39791594560b2326625b663ed6796882900c220f#diff-a9aa0352703240c8ae70f1c83add4bc8R981
     *
     * *      1) windowSwipeToDismiss为true且windowIsTranslucent为false
     * *      2) windowIsTranslucent为true
     * *      3) windowIsFloating为true
     * *      4) orientation满足 ActivityInfo.isFixedOrientation()，即如下列表值
     * *              orientation == SCREEN_ORIENTATION_LANDSCAPE
     * *           || orientation == SCREEN_ORIENTATION_SENSOR_LANDSCAPE
     * *           || orientation == SCREEN_ORIENTATION_REVERSE_LANDSCAPE
     * *           || orientation == SCREEN_ORIENTATION_USER_LANDSCAPE
     * *           || orientation == SCREEN_ORIENTATION_PORTRAIT
     * *           || orientation == SCREEN_ORIENTATION_SENSOR_PORTRAIT
     * *           || orientation == SCREEN_ORIENTATION_REVERSE_PORTRAIT
     * *           || orientation == SCREEN_ORIENTATION_USER_PORTRAIT
     * *           || orientation == SCREEN_ORIENTATION_LOCKED
     * *      5）系统版本为android 8.0（含）及android 8.1 release 1（不含）之间
     * *      6）升级targetSdkVersion到26及以上（注意部分android O系统在targetSdkVersion为26时不会产生此处问题，因google中间有临时代码调整。。。）
     *
     * Note: 1）"Only fullscreen opaque activities can request orientation"崩溃出现条件：
     * activity在super.onCreate()之前满足（1 || 2 || 3） && 4 && 5 && 6
     * 临时解决办法：调整设置orientation位置，去除manifest中项（或改为SCREEN_ORIENTATION_BEHIND），且在activity->super.onCreate()之后调用本方法设置屏幕方向
     * 或在super.onCreate()之前调用{@link #fixFullscreenOpaqueError(Activity)}
     *
     * 2）"Only fullscreen activities can request orientation"崩溃出现条件：
     * requestOrientation时满足（1 || 2 || 3） && 4 && 5 && 6
     * 临时解决办法：调整设置orientation位置，去除manifest中项（或改为SCREEN_ORIENTATION_BEHIND），且在activity->super.onCreate()之后调用本方法设置屏幕方向
     *
     * 3) 如果有动态用代码设置theme及style的activity，注意调整调用逻辑，避免上述问题！
     *
     * @param orientation {@see ActivityInfo.SCREEN_ORIENTATION_XXX}
     */
    public static void setScreenOrientationUnderProtect(Activity activity, int orientation) {
        if (activity == null) {
            return;
        }
        try {
            activity.setRequestedOrientation(orientation);
        } catch (Throwable e) {
            e.printStackTrace();
            try {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            } catch (Throwable err) {
                err.printStackTrace();
            }
        }
    }
    public static boolean isLandscape(Context context) {
        try {
            return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        } catch (Exception e) {
            return false;
        }

    }
}
