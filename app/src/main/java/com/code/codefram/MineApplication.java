package com.code.codefram;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.code.cframe.CFrame;
import com.code.codefram.activity.LoadMultidexActivity;
import com.code.cframe.utils.MultidexUtils;
import com.code.cframe.utils.ProcessUtil;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.code.codefram.activity.LoadMultidexActivity.MULTIDEX_INSTALLED_FLAG_FILE;

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
        }else{
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
        CFrame.getInstance().init(this);
    }
}
