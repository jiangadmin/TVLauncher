package com.jiang.tvlauncher;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.jiang.tvlauncher.server.MyService;
import com.jiang.tvlauncher.utils.LogUtil;
import com.xgimi.xgmapiservice.XGimiApi;

/**
 * Created by  jiang
 * on 2017/7/3.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO
 * update：
 */
public class MyAppliaction extends Application {
    private static final String TAG = "MyAppliaction";
    public static boolean LogShow = true;
    public static Context context;

    XGimiApi apiManager;



    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, MyService.class));
        context = this;
        LogUtil.e(TAG,"准备连接AIDL");
        ComponentName componentName = new ComponentName("com.xgimi.xgmapiservice", "com.xgimi.xgmapiservice.XGimiApiService");
        bindService(new Intent().setComponent(componentName), serviceConnection, Context.BIND_AUTO_CREATE);

    }


    ServiceConnection serviceConnection = new ServiceConnection() {
        //绑定上服务的时候
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtil.e(TAG,"连接AIDL成功");
            //得到远程服务
            apiManager = XGimiApi.Stub.asInterface(iBinder);

            try {
                LogUtil.e(TAG,"序列号："+apiManager.getMachineId());
            } catch (RemoteException e) {
                e.printStackTrace();
                LogUtil.e(TAG,"连接失败"+e.getMessage());
            }
        }

        //断开服务的时候
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtil.e(TAG,"断开AIDL连接");
        }
    };

}
