package com.jiang.tvlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.dialog.NetDialog;


/**
 * Created by  jiang
 * on 2017/6/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 开机广播接收
 * update：
 */
public class BootCompletedReceiver extends BroadcastReceiver {
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
}