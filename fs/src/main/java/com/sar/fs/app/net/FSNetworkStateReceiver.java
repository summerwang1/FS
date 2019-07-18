package com.sar.fs.app.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sar.fs.app.net.FSNetWorkUtil.netType;
import android.util.Log;

import java.util.ArrayList;

/**
 * @auth: sarWang
 * @date: 2019-07-04 19:03
 * @describe
 */

public class FSNetworkStateReceiver extends BroadcastReceiver {
    public static final String TAG = FSNetworkStateReceiver.class.getSimpleName();
    public static final String ABX_ANDROID_NET_CHANGE_ACTION = "abx.android.net.conn.CONNECTIVITY_CHANGE";
    private static final String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private static Boolean networkAvailable = false;
    private static netType netType;
    private static ArrayList<FSNetChangeObserver> netChangeObserverArrayList = new ArrayList();
    private static BroadcastReceiver receiver;

    public FSNetworkStateReceiver() {
    }

    public static void checkNetworkState(Context mContext) {
        Intent intent = new Intent();
        intent.setAction("abx.android.net.conn.CONNECTIVITY_CHANGE");
        mContext.sendBroadcast(intent);
    }

    public static void registerNetworkStateReceiver(Context mContext) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("abx.android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mContext.getApplicationContext().registerReceiver(getReceiver(), filter);
    }

    private static BroadcastReceiver getReceiver() {
        if (receiver == null) {
            receiver = new FSNetworkStateReceiver();
        }

        return receiver;
    }

    public static void unRegisterNetworkStateReceiver(Context mContext) {
        if (receiver != null) {
            try {
                mContext.getApplicationContext().unregisterReceiver(receiver);
            } catch (Exception var2) {
                Log.e(TAG, "unRegisterNetworkStateReceiver: ", var2);
            }
        }

    }

    public static netType getAPNType() {
        return netType;
    }

    public static void registerObserver(FSNetChangeObserver observer) {
        if (netChangeObserverArrayList == null) {
            netChangeObserverArrayList = new ArrayList();
        }

        netChangeObserverArrayList.add(observer);
    }

    public static void removeRegisterObserver(FSNetChangeObserver observer) {
        if (netChangeObserverArrayList != null) {
            netChangeObserverArrayList.remove(observer);
        }

    }

    public void onReceive(Context context, Intent intent) {
        receiver = this;
        if (intent.getAction().equalsIgnoreCase("android.net.conn.CONNECTIVITY_CHANGE") || intent.getAction().equalsIgnoreCase("abx.android.net.conn.CONNECTIVITY_CHANGE")) {
            Log.d(TAG, "onReceive: 网络状态改变");
            if (!FSNetWorkUtil.isNetworkAvailable(context)) {
                Log.d(TAG, "onReceive: 没有网络连接");
                networkAvailable = false;
            } else {
                Log.d(TAG, "onReceive: 网络连接成功");
                netType = FSNetWorkUtil.getAPNType(context);
                networkAvailable = true;
            }

            this.notifyObserver();
        }

    }

    private void notifyObserver() {
        for(int i = 0; i < netChangeObserverArrayList.size(); ++i) {
            FSNetChangeObserver observer = (FSNetChangeObserver)netChangeObserverArrayList.get(i);
            if (observer != null) {
                if (isNetworkAvailable()) {
                    observer.onConnect(netType);
                } else {
                    observer.onDisConnect();
                }
            }
        }

    }

    public static Boolean isNetworkAvailable() {
        return networkAvailable;
    }
}
