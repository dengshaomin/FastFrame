package com.code.demo.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.jpush.android.service.JPushMessageReceiver;
import cn.jpush.android.service.PushService;

public class LifeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent pushintent=new Intent(context, PushService.class);//启动极光推送的服务
        context.startService(pushintent);
    }
}
