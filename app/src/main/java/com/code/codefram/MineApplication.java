package com.code.codefram;

import android.app.Application;

import com.code.codeframlibrary.commons.CodeFram;

/**
 * Created by dengshaomin on 2017/12/6.
 */

public class MineApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CodeFram.init(this);
    }
}
