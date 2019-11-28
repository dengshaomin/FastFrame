package com.code.fastframe.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.code.fastframe.eventbus.EventId.System;
import com.code.fastframe.eventbus.GlobalEvent;
import com.code.fastframe.models.NetEventBean;

import org.greenrobot.eventbus.EventBus;

public class NetworkStatusReceiver extends BroadcastReceiver {

    private int mLastNetworkStatus = NetworkStatusType.NETWORK_STATUS_WIFI;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager connectivityManager = null;
            try {
                connectivityManager = (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
            } catch (Exception e) {

            }
            if (connectivityManager != null) {
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isAvailable()) {
                    switch (netInfo.getType()) {
                        case ConnectivityManager.TYPE_WIFI: {
                            EventBus.getDefault().post(new GlobalEvent(System.NET_CHANGE,new NetEventBean(mLastNetworkStatus,NetworkStatusType.NETWORK_STATUS_WIFI)));
                            mLastNetworkStatus = NetworkStatusType.NETWORK_STATUS_WIFI;
                            break;
                        }
                        case ConnectivityManager.TYPE_MOBILE: {
                            EventBus.getDefault().post(new GlobalEvent(System.NET_CHANGE,new NetEventBean(mLastNetworkStatus,NetworkStatusType.NETWORK_STATUS_MOBILE)));
                            mLastNetworkStatus = NetworkStatusType.NETWORK_STATUS_MOBILE;
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                } else {
                    EventBus.getDefault().post(new GlobalEvent(System.NET_CHANGE,new NetEventBean(mLastNetworkStatus,NetworkStatusType.NETWORK_STATUS_NO)));
                    mLastNetworkStatus = NetworkStatusType.NETWORK_STATUS_NO;
                }
            } else {
            }
        }
    }
}
