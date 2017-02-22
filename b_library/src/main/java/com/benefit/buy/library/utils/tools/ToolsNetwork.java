/* 
 * 文件名：ToolsNetwork.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.benefit.buy.library.utils.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * 网络处理工具〈一句话功能简述〉 〈功能详细描述〉
 * @author qinyulun
 * @version HDMNV100R001, 2014-7-9
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ToolsNetwork {

    private Context mContext;

    public State wifiState = null;

    public State mobileState = null;

    public ToolsNetwork(Context context) {
        mContext = context;
    }

    public enum NetWorkState {
        WIFI, MOBILE, NONE;
    }

    /**
     * 获取当前的网络状态 〈一句话功能简述〉 〈功能详细描述〉
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public NetWorkState getConnectState() {
        ConnectivityManager manager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        manager.getActiveNetworkInfo();
        wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if ((wifiState != null) && (mobileState != null) && (State.CONNECTED != wifiState)
                && (State.CONNECTED == mobileState)) {
            return NetWorkState.MOBILE;
        }
        else if ((wifiState != null) && (mobileState != null) && (State.CONNECTED != wifiState)
                && (State.CONNECTED != mobileState)) {
            return NetWorkState.NONE;
        }
        else if ((wifiState != null) && (State.CONNECTED == wifiState)) {
            return NetWorkState.WIFI;
        }
        return NetWorkState.NONE;
    }

    /**
     * 检查WIFI是否连接
     * @author Ysjian
     * @date 2014-5-9
     * @return 如果连接了返回true，否则返回false
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo != null;
    }

    /**
     * 检查手机网络(4G/3G/2G)是否连接
     * @author Ysjian
     * @date 2014-5-9
     * @return 如果连接了返回true，否则返回false
     */
    public static boolean isMobileNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mobileNetworkInfo != null;
    }

    /**
     * 检查是否有可用网络
     * @author Ysjian
     * @date 2014-5-9
     * @return 存在WIFI和手机数据任意可用网络返回true，否则返回false
     */
    public static boolean hasActivityNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
