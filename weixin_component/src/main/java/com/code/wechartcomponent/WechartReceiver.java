package com.code.wechartcomponent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WechartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        WechartComponent.initWxApi(context);
    }
}
