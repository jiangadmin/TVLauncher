package com.jiang.tvlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.EventLogTags;

import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.utils.LogUtil;

/**
 * Created by  jiang
 * on 2017/7/3.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 安装卸载广播监听
 * update：
 */

public class AppInstallReceiver extends BroadcastReceiver {
    private static final String TAG = "AppInstallReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        //安装
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Loading.dismiss();
            LogUtil.e(TAG,"安装成功");
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (launchIntent != null)
                context.startActivity(launchIntent);
        }
        //卸载
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            LogUtil.e(TAG,"卸载成功");
        }
        //替换
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (launchIntent != null)
                context.startActivity(launchIntent);
            LogUtil.e(TAG,"替换成功");
        }
    }
}