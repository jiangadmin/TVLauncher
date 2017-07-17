package com.jiang.tvlauncher;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.jiang.tvlauncher.server.MyService;
import com.jiang.tvlauncher.servlet.Register_Servlet;

/**
 * Created by  jiang
 * on 2017/7/3.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO
 * update：
 */
public class MyAppliaction extends Application {
    public static boolean LogShow = true;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, MyService.class));
        context =this;
    }
}
