package com.code.fastframe.net;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;


import androidx.annotation.RequiresPermission;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.LinkedList;

import com.code.fastframe.FastFrame;

import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.INTERNET;
import static android.content.Context.WIFI_SERVICE;

/**
 * 荣耀6Plus实验结果：
 * 只要不是飞行模式，mobile都是Available，只有4G，没有wifi连接上的情况下，mobile是connected
 * 只要不是飞行模式，wifi都是Available,wifi连接上的情况下，wifi是connected
 * 如果activeNetworkInfo有数据，则connected 和 available都为true
 * isAvailable():Indicates whether network connectivity is possible.表明网络连接是否posible，posible可能的意思，而并不是已经连接
 * isConnected():Indicates whether network connectivity exists and it is possible to establish connections and pass
 * data.表明网络连接是否存在并且可以传递数据。
 */
public class NetWorkTypeUtils {

    private static final String TAG = "NetWorkTypeUtils";

    private static final String NETTYPE_INVALID_CONTEXT = "-999";

    private static final String NETTYPE_UNKNOWN = "-1";

    private static final int NETWORK_TYPE_NR = 20;

    private static NetworkInfo mNetworkInfo;
    private static String mWlanMacAddressCache;

    /**
     * 获取网络信息
     *
     * @param context 上下文
     * @return 网络信息
     */
    @SuppressLint({"WrongConstant", "MissingPermission"})
    public static NetworkInfo getAvailableNetWorkInfo(Context context) {
        if (context == null) {
            return null;
        }
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return null;
        }
        NetworkInfo activeNetInfo = null;
        try {
            activeNetInfo = connectivityManager.getActiveNetworkInfo();
        } catch (RuntimeException e) {
            /**
             * ava.lang.RuntimeException: android.os.DeadSystemException
             * at android.net.ConnectivityManager.getActiveNetworkInfo(ConnectivityManager.java:905)
             * at com.qiyi.baselib.net.NetWorkTypeUtils.getAvailableNetWorkInfo(NetWorkTypeUtils.java:74)
             * at com.qiyi.baselib.net.NetWorkTypeUtils.isNetAvailable(NetWorkTypeUtils.java:190)
             */
            return null;
        }
        if (activeNetInfo != null && activeNetInfo.isConnected()) {
            return activeNetInfo;
        } else {
            return getExtraNetworkInfo(connectivityManager);
        }
    }

    public static void setNetworkInfo(NetworkInfo networkInfo) {
        mNetworkInfo = networkInfo;
    }

    /**
     * 获取网络信息 (若缓存数据不为空则先返回缓存数据，否则去获取网络信息)
     * 注意：除主进程，插件进程，下载进程外其它进程，插件或独立APP使用该方法前需要监听网络变化，动态修改缓存数据
     *
     * @param context 上下文
     * @return 网络信息
     */
    public static NetworkInfo getAvailableNetWorkInfoWithCache(Context context) {
        if (context == null) {
            return null;
        }
        if (mNetworkInfo != null) {
            return mNetworkInfo;
        }
        mNetworkInfo = getAvailableNetWorkInfo(context);
        return mNetworkInfo;
    }


    /**
     * 在获取不到已经连接的网络类型时，再尝试从all network info中去获取
     * <p>
     * 所有网络类型：
     * MOBILE is connected false isAvailable true
     * WIFI is connected false isAvailable true
     * MOBILE_MMS is connected false isAvailable true
     * MOBILE_SUPL is connected false isAvailable true
     * MOBILE_DUN is connected false isAvailable true
     * MOBILE_HIPRI is connected false isAvailable true
     * BLUETOOTH is connected false isAvailable true
     * MOBILE_FOTA is connected false isAvailable true
     * MOBILE_IMS is connected false isAvailable true
     * MOBILE_CBS is connected false isAvailable true
     * MOBILE_EMERGENCY is connected false isAvailable true
     * VPN is connected false isAvailable true
     *
     * @return networkInfo
     */
    @SuppressLint("MissingPermission")
    public static NetworkInfo getExtraNetworkInfo(ConnectivityManager connectivityManager) {
        if (connectivityManager == null) {
            return null;
        }
        NetworkInfo activeNetInfo = null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //api<23 required
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if (networkInfos != null && networkInfos.length > 0) {
                for (NetworkInfo networkInfo : networkInfos) {
                    if (networkInfo != null && networkInfo.isConnected()) {
                        return networkInfo;
                    }
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //api>=21 reqired
            Network[] networks = connectivityManager.getAllNetworks();
            if (networks != null && networks.length > 0) {
                for (Network network : networks) {
                    //忽略vpn
                    NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(network);
                    if (caps == null) {
                        continue;
                    }
                    boolean transportVpn = caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
                    if (transportVpn) {
                        continue;
                    }
                    try {
                        if (network != null) {
                            activeNetInfo = connectivityManager.getNetworkInfo(network);
                        }
                    } catch (NullPointerException e) {
                        /**
                         * Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String android.net.NetworkInfo$State.name()' on a null object reference
                         * at android.os.Parcel.readException(Parcel.java:1608)
                         * at android.os.Parcel.readException(Parcel.java:1555)
                         * at android.net.IConnectivityManager$Stub$Proxy.getNetworkInfoForNetwork(IConnectivityManager.java:1183)
                         * at android.net.ConnectivityManager.getNetworkInfo(ConnectivityManager.java:800)
                         * at com.qiyi.baselib.net.NetWorkTypeUtils.getExtraNetworkInfo(NetWorkTypeUtils.java:155)
                         */
                    } catch (RuntimeException e) {
                        /**
                         * Caused by: java.lang.RuntimeException: android.os.DeadSystemException
                         * 	at android.net.ConnectivityManager.getNetworkInfoForUid(ConnectivityManager.java:1108)
                         * 	at android.net.ConnectivityManager.getNetworkInfo(ConnectivityManager.java:1100)
                         * 	at com.qiyi.baselib.net.NetWorkTypeUtils.getExtraNetworkInfo(NetWorkTypeUtils.java:71)
                         */
                    }

                    if (activeNetInfo != null && activeNetInfo.isConnected()) {
                        if (caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                            return activeNetInfo;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * 是否有网络
     *
     * @param context 上下文
     * @return false：无网，否则返回true
     */
    public static boolean isNetAvailable(Context context) {
        return getAvailableNetWorkInfo(context) != null;
    }

    /**
     * 获取网络类型
     *
     * @param context 上下文
     * @return 网络类型
     */
    public static String getNetWorkType(Context context) {
        if (context == null) {
            return NETTYPE_INVALID_CONTEXT;
        }
        NetworkInfo netWorkInfo = getAvailableNetWorkInfo(context);
        return getNetWorkType(context, netWorkInfo);
    }

    /**
     * 获取网络信息 (若缓存数据不为空则先返回缓存数据，否则去获取网络信息)
     * !!!慎用 注意：除主进程，插件进程，下载进程外其它进程，插件或独立APP使用该方法前需要监听网络变化，动态修改缓存数据
     *
     * @param context 上下文
     * @return 网络类型
     */
    @SuppressLint("WrongConstant")
    public static String getNetWorkTypeWithCache(Context context) {
        if (context == null) {
            return NETTYPE_INVALID_CONTEXT;
        }
        NetworkInfo netWorkInfo = getAvailableNetWorkInfoWithCache(context);
        return getNetWorkType(context, netWorkInfo);
    }

    @SuppressLint("MissingPermission")
    private static String getNetWorkType(Context context, NetworkInfo netWorkInfo) {
        if (context == null) {
            return NETTYPE_INVALID_CONTEXT;
        }
        String netWorkType = "-1000";
        if (netWorkInfo == null) {
            return NETTYPE_UNKNOWN;
        }
        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            netWorkType = "1";
        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            switch (telephonyManager.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    netWorkType = "2";
                    break;
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    netWorkType = "3";
                    break;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    netWorkType = "4";
                    break;
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    netWorkType = "5";
                    break;
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    netWorkType = "6";
                    break;
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    netWorkType = "7";
                    break;
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    netWorkType = "8";
                    break;
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    netWorkType = "9";
                    break;
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    netWorkType = "10";
                    break;
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    netWorkType = "11";
                    break;
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    netWorkType = "12";
                    break;
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    netWorkType = "13";
                    break;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    netWorkType = "14";
                    break;
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                    netWorkType = "15";
                    break;
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    netWorkType = "16";
                    break;
                case TelephonyManager.NETWORK_TYPE_GSM:
                    netWorkType = "17";
                    break;
                case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    netWorkType = "18";
                    break;
                case TelephonyManager.NETWORK_TYPE_IWLAN:
                    netWorkType = "19";
                    break;
                case NETWORK_TYPE_NR://TelephonyManager.NETWORK_TYPE_NR,if compilesdk<29 replaced by 20
                    netWorkType = "20";
                    break;
                default:
                    netWorkType = NETTYPE_UNKNOWN;
            }
            // 20-24 reserved for mobile type
        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_BLUETOOTH) {
            netWorkType = "25";
        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_VPN) {
            netWorkType = "26";
        }
        return netWorkType;
    }

    /**
     * 获取网络状态，不区分3G和4G
     *
     * @param context 上下文
     * @return 网络状态
     */
    public static NetworkStatus getNetworkStatus(Context context) {
        NetworkStatus status = getNetworkStatusFor4G(context);
        if (status == NetworkStatus.MOBILE_4G) {
            return NetworkStatus.MOBILE_3G;
        } else {
            return status;
        }
    }

    /**
     * 获取网络状态,区分网络类型，包括2G/3G/4G/5，WIFI,OFF
     *
     * @param context 上下文
     * @return 网络状态
     */
    public static NetworkStatus getNetworkStatusFor4G(Context context) {
        NetworkInfo networkInfo = getAvailableNetWorkInfo(context);
        if (null == networkInfo) {
            return NetworkStatus.OFF;
        }
        int type = networkInfo.getType();
        if (ConnectivityManager.TYPE_WIFI == type) {
            return NetworkStatus.WIFI;
        }
        return getNetworkStatusByType(context);
    }

    /**
     * 使用缓存获取网络状态
     * (若缓存数据不为空则先返回缓存数据，否则去获取网络信息)
     * 注意：除主进程，插件进程，下载进程外其它进程，插件或独立APP使用该方法前需要监听网络变化，动态修改缓存数据
     *
     * @param context 上下文
     * @return 网络状态
     */
    public static NetworkStatus getNetworkStatusFor4GWithCache(Context context) {
        // 使用带缓存的方法
        NetworkInfo networkInfo = getAvailableNetWorkInfoWithCache(context);
        if (null == networkInfo) {
            return NetworkStatus.OFF;
        }
        int type = networkInfo.getType();
        if (ConnectivityManager.TYPE_WIFI == type) {
            return NetworkStatus.WIFI;
        }
        return getNetworkStatusByType(context);
    }


    /**
     * 获取网络制式
     *
     * @return NetworkStatus.MOBILE_2G ：2G网络
     * NetworkStatus.MOBILE_3G ：3G网络
     * NetworkStatus.MOBILE_4G ：4G网络
     * NetworkStatus.MOBILE_5G ：5G网络
     */
    @SuppressLint("WrongConstant")
    public static NetworkStatus getNetworkStatusByType(Context context) {
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int type = -1;
        if (telephonyManager != null) {
            type = telephonyManager.getNetworkType();
            switch (type) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return NetworkStatus.MOBILE_2G;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return NetworkStatus.MOBILE_4G;
                case NETWORK_TYPE_NR:
                    //5G网络
                    return NetworkStatus.MOBILE_5G;
                default:
                    return NetworkStatus.MOBILE_3G;
            }
        } else {
            //保持与先前的逻辑一致，异常情况判断为3G网络
            return NetworkStatus.MOBILE_3G;
        }
    }

    /**
     * 获取网络制式
     *
     * @return -1:unknown 0:wifi，2:2G 3:3G 4:4G 5：5G
     */
    @SuppressLint("WrongConstant")
    public static String getNetworkClassByType(Context context) {
        NetworkInfo netWorkInfo = NetWorkTypeUtils.getAvailableNetWorkInfo(context);
        if (netWorkInfo == null) {
            return "-1";
        }
        String networkType = "-1";
        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            networkType = "0";
        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null) {
                return "-1";
            }
            switch (telephonyManager.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                case TelephonyManager.NETWORK_TYPE_GSM:
                    networkType = "2";
                    break;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    networkType = "3";
                    break;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    networkType = "4";
                    break;
                case NETWORK_TYPE_NR:
                    //5G网络
                    networkType = "5";
                    break;
                default:
                    networkType = "-1";
                    break;
            }
        }
        return networkType;
    }


    /**
     * 是否是蜂窝网络
     *
     * @param context 上下文
     * @return true：当前网络是蜂窝网络，否则返回false
     */
    public static boolean isMobileNetwork(Context context) {
        NetworkStatus status = getNetworkStatusFor4G(context);
        if (status != NetworkStatus.WIFI && status != NetworkStatus.OFF) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 是否是蜂窝网络
     *
     * @param status 当前网络状态
     * @return true：当前网络是蜂窝网络，否则返回false
     */
    public static boolean isMobileNetwork(NetworkStatus status) {
        if (status != NetworkStatus.WIFI && status != NetworkStatus.OFF) {
            return true;
        } else {
            return false;
        }
    }

    @Deprecated
    @SuppressLint({"MissingPermission", "WrongConstant"})
    public static String getNetWorkApnType(Context context) {
        if (null == context) {
            return null;
        }
        String mApnName = null;
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = null;
            try {
                info = cm.getActiveNetworkInfo();
            } catch (RuntimeException e) {
                /**
                 * ava.lang.RuntimeException: android.os.DeadSystemException
                 * at android.net.ConnectivityManager.getActiveNetworkInfo(ConnectivityManager.java:905)
                 * at com.qiyi.baselib.net.NetWorkTypeUtils.getAvailableNetWorkInfo(NetWorkTypeUtils.java:74)
                 * at com.qiyi.baselib.net.NetWorkTypeUtils.isNetAvailable(NetWorkTypeUtils.java:190)
                 */
                return mApnName;
            }
            if (info != null) {

                mApnName = "";
                if (info.getTypeName() != null) {
                    mApnName = info.getTypeName().toLowerCase(); // WIFI/MOBILE
                }

                if ("wifi".equalsIgnoreCase(mApnName)) {
                    mApnName = "wifi";
                } else {
                    mApnName = "";
                    if (info.getExtraInfo() != null) {
                        // 3gnet/3gwap/uninet/uniwap/cmnet/cmwap/ctnet/ctwap
                        mApnName = info.getExtraInfo().toLowerCase();
                    }
                }
            }
        }
        return mApnName;
    }


    /*********************************new utils******************************************************/
    /**
     * 打开WiFI设置界面
     */
    public static void openWirelessSettings() {
        FastFrame.mApplicationContext.startActivity(
                new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        );
    }

    /**
     * 判断wifi是否打开.
     * <p>需添加权限：{@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />}</p>
     *
     * @return {@code true}: 打开 <br>{@code false}: 关闭
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static boolean getWifiEnabled() {
        @SuppressLint("WifiManagerLeak")
        WifiManager manager = (WifiManager) FastFrame.mApplicationContext.getSystemService(WIFI_SERVICE);
        return manager != null && manager.isWifiEnabled();
    }

    /**
     * 打开或关闭wifi.
     * <p>需添加权限：{@code <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />}</p>
     *
     * @param enabled True 打开, false 关闭.
     */
    @RequiresPermission(CHANGE_WIFI_STATE)
    public static void setWifiEnabled(final boolean enabled) {
        @SuppressLint("WifiManagerLeak")
        WifiManager manager = (WifiManager) FastFrame.mApplicationContext.getSystemService(WIFI_SERVICE);
        if (manager == null || enabled == manager.isWifiEnabled()) {
            return;
        }
        manager.setWifiEnabled(enabled);
    }

    /**
     * 获取网络运营商名称.
     *
     * @return 网络运营商名称
     */
    public static String getNetworkOperatorName() {
        TelephonyManager tm =
                (TelephonyManager) FastFrame.mApplicationContext.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : "";
    }

    /**
     * 获取域名ip地址.
     * <p>需添加权限{@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param domain 需添加权限.
     * @return 域名ip地址
     */
    @RequiresPermission(INTERNET)
    public static String getDomainAddress(final String domain) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(domain);
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            return "";
        }
    }

    /**
     * 获取wifi服务器地址.
     *
     * @return wifi服务器地址
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getServerAddressByWifi() {
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) FastFrame.mApplicationContext.getSystemService(Context.WIFI_SERVICE);
        if (wm == null) {
            return "";
        }
        return Formatter.formatIpAddress(wm.getDhcpInfo().serverAddress);
    }

    /**
     * 获取IP地址.
     * <p>需添加权限：{@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param useIPv4 True：使用Ipv4, false：不使用.
     * @return IP地址
     */
    @RequiresPermission(INTERNET)
    public static String getIPAddress(final boolean useIPv4) {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            LinkedList<InetAddress> adds = new LinkedList<>();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                // 防止小米手机返回10.0.2.15
                if (!ni.isUp() || ni.isLoopback()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    adds.addFirst(addresses.nextElement());
                }
            }
            for (InetAddress add : adds) {
                if (!add.isLoopbackAddress()) {
                    String hostAddress = add.getHostAddress();
                    boolean isIPv4 = hostAddress.indexOf(':') < 0;
                    if (useIPv4) {
                        if (isIPv4) {
                            return hostAddress;
                        }
                    } else {
                        if (!isIPv4) {
                            int index = hostAddress.indexOf('%');
                            return index < 0
                                    ? hostAddress.toUpperCase()
                                    : hostAddress.substring(0, index).toUpperCase();
                        }
                    }
                }
            }
        } catch (SocketException e) {
        }
        return "";
    }

    public static void setWlanMacAddressCache(String wlanMacAddressCache) {
        mWlanMacAddressCache = wlanMacAddressCache;
    }

    /**
     * 获取wlan Mac地址
     *
     * @param context 上下文
     * @return 返回wlan mac地址
     */
    @SuppressLint("MissingPermission")
    public static String getWlanMacAddress(Context context) {
        if (context == null || context.getApplicationContext() == null) {
            return "";
        }

        boolean hasPermission = false;
        try {
            hasPermission = context.checkPermission(
                    Manifest.permission.ACCESS_WIFI_STATE, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED;
        } catch (RuntimeException e) {
            hasPermission = false;
            /**
             * Caused by: java.lang.RuntimeException: android.os.DeadSystemException
             * 	at android.app.ContextImpl.checkPermission(ContextImpl.java:1877)
             * 	at android.content.ContextWrapper.checkPermission(ContextWrapper.java:718)
             * 	at com.qiyi.baselib.net.NetWorkTypeUtils.getWlanMacAddress(NetWorkTypeUtils.java:21)
             * 	at com.qiyi.baselib.net.NetworkChangeReceiver.onReceive(NetworkChangeReceiver.java:61)
             */
        }
        if (hasPermission) {
            WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wm != null) {
                WifiInfo info = wm.getConnectionInfo();
                if (info != null && !TextUtils.isEmpty(info.getBSSID())) {
                    return info.getBSSID();
                }
            }
        }
        return "";
    }

    /**
     * 获取wlan Mac地址 (若缓存数据不为空则先返回缓存数据，否则去获取网络信息)
     * 注意：除主进程，插件进程，下载进程外其它进程，插件或独立APP使用该方法前需要监听网络变化，动态修改缓存数据
     *
     * @param context 上下文
     * @return 返回wlan mac地址
     */
    public static String getWlanMacAddressWithCache(Context context) {
        if (!TextUtils.isEmpty(mWlanMacAddressCache)) {
            return mWlanMacAddressCache;
        }
        if (context == null) {
            return "";
        }
        mWlanMacAddressCache = getWlanMacAddress(context);
        return mWlanMacAddressCache;
    }
}
