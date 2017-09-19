package com.jiang.tvlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.activity.Home_Activity;


/**
 * Created by  jiang
 * on 2017/6/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 开机广播接收
 * update：
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    private NetReceiver netReceiver = new NetReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            //example:启动程序
//            Intent start = new Intent(context, Home_Activity.class);
//            start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//
//            context.startActivity(start);
            //注册广播
//            registerNetworkReceiver();
        }
    }

    /**
     * 注册网络广播
     */
    private void registerNetworkReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        MyAppliaction.context.registerReceiver(netReceiver, filter);
    }
}