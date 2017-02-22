/* 
 * 文件名：ReConnectService.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;

import com.benefit.buy.library.views.ToastView;

/**
 * 监听网络状态
 * @author qinyulun
 * @version HDMNV100R001, 2013-12-4
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ReConnectService extends Service {

    private ConnectivityManager connectivityManager;

    private NetworkInfo info;

    @Override
    public void onCreate() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(reConnectionBroadcastReceiver, mFilter);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(reConnectionBroadcastReceiver);
        super.onDestroy();
    }

    BroadcastReceiver reConnectionBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                Log.d("mark", "网络状态已经改变");
                connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();
                if ((info != null) && info.isAvailable()) {
                }
                else {
                    ToastView.makeText(context, "网络断开...").setGravity(Gravity.CENTER, 0, 0).show();
                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
