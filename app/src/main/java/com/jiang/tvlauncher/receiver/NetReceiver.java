package com.jiang.tvlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.servlet.TurnOn_servlet;
import com.jiang.tvlauncher.utils.LogUtil;

/**
 * Created by  jiang
 * on 2017/7/15.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 网络广播
 * update：
 */
public class NetReceiver extends BroadcastReceiver {
    private static final String TAG = "NetReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo.State wifiState = null;
        NetworkInfo.State mobileState = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED == mobileState) {
            setTurnOn();
            // 手机网络连接成功
        } else if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED != mobileState) {
            Toast.makeText(context, "手机没有任何的网络", Toast.LENGTH_SHORT).show();
            // 手机没有任何的网络
        } else if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
            // 无线网络连接成功
            setTurnOn();
        }
    }


    /**
     * 发出开机数据
     */
    public void setTurnOn() {

//        if (Const.Nets)
//            new TurnOn_servlet().execute();
    }
}
