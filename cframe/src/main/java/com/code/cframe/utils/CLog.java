package com.code.cframe.utils;

import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import android.util.Log;

import com.code.cframe.CFrame;

public class CLog {

    public static Boolean DEBUGMODE;

    private static final String TAG = "CLog";

    public static void e(String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        if (DEBUGMODE) {
            Log.e(TAG, s);
        }
    }

    public static void d(String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        if (DEBUGMODE) {
            Log.d(TAG, s);
        }
    }

    public static void i(String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        if (DEBUGMODE) {
            Log.i(TAG, s);
        }
    }

    public static void v(String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        if (DEBUGMODE) {
            Log.v(TAG, s);
        }
    }

    public static boolean isApkInDebug() {
        try {
            ApplicationInfo info = CFrame.getInstance().mApplicationContext.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static void init() {
        DEBUGMODE = isApkInDebug();
    }
}
