package com.kaqi.niuniu.ireader.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import com.kaqi.niuniu.ireader.App;

/**
 * Created by newbiechen on 17-5-11.
 */

public class NetworkUtils {


    /**
     * 获取活动网络信息
     * @return NetworkInfo
     */
    public static NetworkInfo getNetworkInfo(){
        ConnectivityManager cm = (ConnectivityManager) App
                .getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo();
    }

    /**
     * 网络是否可用
     * @return
     */
    public static boolean isAvailable(){
        NetworkInfo info = getNetworkInfo();
        return info != null && info.isAvailable();
    }

    /**
     * 网络是否连接
     * @return
     */
    public static boolean isConnected(){
        NetworkInfo info = getNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * 判断wifi是否连接状态
     * <p>需添加权限 {@code <uses-permission android:name="android.permission
     * .ACCESS_NETWORK_STATE"/>}</p>
     *
     * @param context 上下文
     * @return {@code true}: 连接<br>{@code false}: 未连接
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }


    /**
     * 获取当前连接wifi的名称
     *
     * @return
     */
    public static String getConnectWifiSsid(Context context) {
        if (isWifiConnected(context)) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            return wifiInfo.getSSID();
        }
        return null;
    }

    /**
     * 获取当前连接wifi的名称
     *
     * @return
     */
    public static String getConnectWifiIp(Context context) {
        if (isWifiConnected(context)) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            if (ipAddress == 0) {
                return null;
            }
            return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                    + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
        }
        return null;
    }

    /*
     * 判断设备 是否使用代理上网
     * */
    public static boolean isWifiProxy(Context context) {

        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;

        String proxyAddress;

        int proxyPort;

        if (IS_ICS_OR_LATER) {

            proxyAddress = System.getProperty("http.proxyHost");

            String portStr = System.getProperty("http.proxyPort");

            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));

        } else {

            proxyAddress = android.net.Proxy.getHost(context);

            proxyPort = android.net.Proxy.getPort(context);

        }

        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);

    }

}
