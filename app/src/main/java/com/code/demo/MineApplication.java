package com.code.demo;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.code.demo.activity.LoadMultidexActivity;
import com.code.fastframe.FastFrame;
import com.code.fastframe.utils.MultidexUtils;
import com.code.fastframe.utils.ProcessUtil;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jpush.android.api.JPushInterface;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by dengshaomin on 2017/12/6.
 */

public class MineApplication extends Application {

    private File multidexFlagFile = null;

    private File multidexInstalledFlagFile = null;

    private String multidex_flag_file_name = "multidex_flag.tmp";

    private String multidex_flag_installed_file_name = "multidex_installed_flag.tmp";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        multidexInstalledFlagFile = new File(base.getCacheDir().getAbsoluteFile(), multidex_flag_installed_file_name);
        //主进程并且vm不支持多dex的情况下才使用 Multidex
        if (!multidexInstalledFlagFile.exists() && ProcessUtil.isMainAppProcess(base)
                && MultidexUtils.supportMultidex()) {
            loadMultiDex(base);
        } else {
            MultiDex.install(base);
        }
    }

    private void loadMultiDex(final Context context) {
        createMultidexFlagFile(context);
        //启动多进程去加载MultiDex
        Intent intent = new Intent(context, LoadMultidexActivity.class);
        intent.putExtra(LoadMultidexActivity.MULTIDEX_FLAG_FILE, multidexFlagFile.getPath());
        intent.putExtra(LoadMultidexActivity.MULTIDEX_INSTALLED_FLAG_FILE,
                multidexInstalledFlagFile.getPath());
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //阻塞当前线程，在Application中是不会出现anr的
        while (multidexFlagFile.exists()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MultiDex.install(context);
    }

    private void createMultidexFlagFile(Context context) {
        try {
            multidexFlagFile = new File(context.getCacheDir().getAbsolutePath(), multidex_flag_file_name);
            if (!multidexFlagFile.exists()) {
                multidexFlagFile.createNewFile();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (ProcessUtil.isMainAppProcess(getApplicationContext())) {
            initXinGe();
            initJPush();
            initBugly();
        }
    }

    private void initXinGe() {
        XGPushConfig.enableDebug(this, BuildConfig.DEBUG);
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                Log.e("TPush", "注册成功，设备token为：" + data);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
        XGPushManager.setTag(this, "XINGE");
    }

    private void initJPush() {
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
    }

    private void initBugly() {
        if (!BuildConfig.DEBUG) {
            CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY_APP_ID, BuildConfig.DEBUG);
        }
        if (BuildConfig.BUGLY_APP_UPDATE) {
            Bugly.init(getApplicationContext(), BuildConfig.BUGLY_APP_ID, BuildConfig.DEBUG);
        }
    }
}
