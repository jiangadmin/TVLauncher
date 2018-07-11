package com.jiang.tvlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.servlet.DownUtil;
import com.jiang.tvlauncher.servlet.GetVIP_Servlet;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.ShellUtils;
import com.jiang.tvlauncher.utils.Tools;

/**
 * @author: jiangyao
 * @date: 2018/7/6
 * @Email: www.fangmu@qq.com
 * @Phone: 186 6120 1018
 * TODO:  逻辑科技调用
 */
public class AppBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "AppBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String packname = Const.TvViedo;
        //验证是否有此应用
        if (Tools.isAppInstalled(packname)) {
            //如果要启动定制版腾讯视频
            if (packname.equals(Const.TvViedo)) {
                //判断是否已经运行
                if (!TextUtils.isEmpty(ShellUtils.execCommand("ps |grep com.ktcp.tvvideo:webview", false).successMsg)) {
                    MyAppliaction.activity.startActivity(new Intent(MyAppliaction.activity.getPackageManager().getLaunchIntentForPackage(packname)));
                } else {
                    Loading.show(MyAppliaction.activity, "请稍后");
                    //获取VIP账号
                    new GetVIP_Servlet(true).execute();
                }
            } else {
                MyAppliaction.activity.startActivity(new Intent(MyAppliaction.activity.getPackageManager().getLaunchIntentForPackage(packname)));
            }
        } else {
            Loading.show(MyAppliaction.activity, "请稍后");
            new DownUtil(MyAppliaction.activity).downLoad(SaveUtils.getString(Const.TvViedoDow), packname + ".apk", true);
        }
    }
}
