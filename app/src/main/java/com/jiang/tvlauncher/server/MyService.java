package com.jiang.tvlauncher.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jiang.tvlauncher.utils.LogUtil;


/**
 * Created by  jiang
 * on 2017/7/4.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 定时服务
 * update：
 */
public class MyService extends Service {
    private static final String TAG = "MyService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e(TAG,"启动服务");

    }
}
