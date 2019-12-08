package com.code.demo.jpush;

import android.content.Context;

import cn.jpush.android.service.JPushMessageReceiver;

public class JPushReceiver extends JPushMessageReceiver {

    @Override
    public void onRegister(Context context, String s) {
        super.onRegister(context, s);
    }
}
