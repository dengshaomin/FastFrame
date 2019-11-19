package com.code.cframe.utils;

import android.content.Context;

import com.code.cframe.net.NetWorkTypeUtils;
import com.code.cframe.net.NetworkStatus;

public class NetWorkUtils {

    /**
     * 是否为移动网络
     */
    public static boolean isMobileNetWork(Context context) {
        NetworkStatus status = NetWorkTypeUtils.getNetworkStatus(context);
        return status == NetworkStatus.MOBILE_2G || status == NetworkStatus.MOBILE_3G || status == NetworkStatus.MOBILE_4G;
    }

    /**
     * 是否为wifi网络
     */
    public static boolean isWifiNetWork(Context context) {
        NetworkStatus status = NetWorkTypeUtils.getNetworkStatus(context);
        return status == NetworkStatus.WIFI;
    }

    /**
     * 是否为无网络状态
     */
    public static boolean isOffNetWork(Context context) {
        NetworkStatus status = NetWorkTypeUtils.getNetworkStatus(context);
        return status == NetworkStatus.OFF;
    }

    public static boolean isMobileNetwork(NetworkStatus status) {
        if (status != null) {
            if (status == NetworkStatus.MOBILE_4G || status == NetworkStatus.MOBILE_3G || status ==
                    NetworkStatus.MOBILE_2G) {
                return true;
            }
        }
        return false;
    }

}
