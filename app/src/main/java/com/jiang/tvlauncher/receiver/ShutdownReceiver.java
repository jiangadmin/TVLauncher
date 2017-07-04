package com.jiang.tvlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jiang.tvlauncher.servlet.TurnOff_servlet;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by  jiang
 * on 2017/6/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 关机广播接收
 * update：
 */
public class ShutdownReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
            //关机发送请求
            new TurnOff_servlet().execute();

            //example:写入文件
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(
                        android.os.Environment.getExternalStorageDirectory()
                                + File.separator + "SysLog.txt", true);
                fos.write("系统退出".getBytes("utf-8"));
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}