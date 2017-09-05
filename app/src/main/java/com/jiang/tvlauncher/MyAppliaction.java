package com.jiang.tvlauncher;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiang.tvlauncher.entity.Point;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.servlet.TurnOn_servlet;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.bugly.crashreport.CrashReport;
import com.xgimi.xgimiapiservice.XgimiApiManager;

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

    public static XgimiApiManager apiManager;

    public static String ID = "FFFFFF";
    public static String Temp = "FFFFFF";
    public static String WindSpeed = "FFFFFF";

    Point point;

    public static boolean TurnOnS = false;

    @Override
    public void onCreate() {
        super.onCreate();
//        startService(new Intent(this, TimingService.class));
        context = this;
        //初始化ImageLoader
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));

        CrashReport.initCrashReport(getApplicationContext(), "948ab2c9a7", false);

        SaveUtils.setBoolean(Save_Key.FristTurnOn, true);
        LogUtil.e(TAG, "准备连接AIDL");
        ComponentName componentName = new ComponentName("com.xgimi.xgimiapiservice", "com.xgimi.xgimiapiservice.XgimiApiService");
        bindService(new Intent().setComponent(componentName), serviceConnection, Context.BIND_AUTO_CREATE);

        LogUtil.e(TAG,"定时："+SaveUtils.getInt(Save_Key.Timming));

    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        //绑定上服务的时候
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtil.e(TAG, "连接AIDL成功");
            //得到远程服务
            apiManager = XgimiApiManager.Stub.asInterface(iBinder);

            try {
                ID = apiManager.get("getMachineId", null, null);
                WindSpeed = apiManager.get("getWindSpeed", null, null);
                Temp = apiManager.get("getTemp", null, null);

                apiManager.set("setKeyStoneByPoint", "0", "0", "0", null);

                LogUtil.e(TAG, " 序列号 ：" + apiManager.get("getMachineId", null, null));
                LogUtil.e(TAG, "全局缩放：" + apiManager.get("getZoomValue", null, null));
                LogUtil.e(TAG, "横向缩放：" + apiManager.get("getHorizentalValue", null, null));
                LogUtil.e(TAG, "纵向缩放：" + apiManager.get("getVerticalValue", null, null));
                LogUtil.e(TAG, "标识数据：" + apiManager.get("getMachineSignal", null, null));
                LogUtil.e(TAG, "设备名称：" + apiManager.get("getDeviceName", null, null));
                LogUtil.e(TAG, "亮度模式：" + apiManager.get("getLedMode", null, null));
                LogUtil.e(TAG, "风   速：" + apiManager.get("getWindSpeed", null, null));
                LogUtil.e(TAG, "投影模式：" + apiManager.get("getProjectionMode", null, null));
                LogUtil.e(TAG, "温   度：" + apiManager.get("getTemp", null, null));
                LogUtil.e(TAG, " 开机源 ：" + apiManager.get("getBootSource", null, null));
                LogUtil.e(TAG, "上电开机：" + apiManager.get("getPowerOnStartValue", null, null));
                LogUtil.e(TAG, "梯形角度：" + apiManager.get("getKeyStoneData", null, null));

                String jsonStr = apiManager.get("getKeyStoneData", null, null);

                try {
                    point = new Gson().fromJson(jsonStr, Point.class);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    point = null;
                }

                if (!TextUtils.isEmpty(ID))
                    SaveUtils.setString(Save_Key.SerialNum, ID);
//                new Register_Servlet(MyAppliaction.this).execute();


                new TurnOn_servlet(context).execute();

//                new Update_Servlet().execute();

            } catch (RemoteException e) {
                e.printStackTrace();
                LogUtil.e(TAG, "连接失败" + e.getMessage());
            }
        }

        //断开服务的时候
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtil.e(TAG, "断开AIDL连接");
        }
    };

}
