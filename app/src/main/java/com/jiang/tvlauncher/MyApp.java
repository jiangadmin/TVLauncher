package com.jiang.tvlauncher;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import com.google.gson.Gson;
import com.jiang.tvlauncher.entity.Point;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.servlet.TurnOn_servlet;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.Tools;
import com.tencent.bugly.crashreport.CrashReport;
import com.xgimi.xgimiapiservice.XgimiApiManager;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * Created by  jiang
 * on 2017/7/3.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO
 * update：
 */

public class MyApp extends Application {
    private static final String TAG = "MyAppliaction";
    public static boolean LogShow = true;
    public static Context context;

    public static XgimiApiManager apiManager;

    public static boolean IsLineNet = true;//是否是有线网络
    public static String modelNum = "Z5极光";
    public static String ID = "";
    public static String SN = "";
    public static String Temp = "FFFFFF";
    public static String WindSpeed = "FFFFFF";
    public static String turnType = "2";//开机类型 1 通电开机 2 手动开机
    Point point;
    public static boolean TurnOnS = false;

    public static Activity activity;

    /**
     * 判定是否是极米设备
     */
    public static boolean isxgimi = false;
    private static final String MIPUSH_APP_ID = "2882303761517701199";
    private static final String MIPUSH_APP_KEY = "5501770168199";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //初始化push推送服务
        if (shouldInit()) {
            MiPushClient.registerPush(this, MIPUSH_APP_ID, MIPUSH_APP_KEY);
        }

        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);

        //崩溃检测
        CrashReport.initCrashReport(this, "948ab2c9a7", false);

        LogUtil.e(TAG, "有线连接：" + Tools.isLineConnected());
        Tools.setScreenOffTime(24 * 60 * 60 * 1000);
        LogUtil.e(TAG, "休眠时间：" + Tools.getScreenOffTime());

        SaveUtils.setBoolean(Save_Key.FristTurnOn, true);

        LogUtil.e(TAG, "准备连接AIDL");
        ComponentName componentName = new ComponentName("com.xgimi.xgimiapiservice", "com.xgimi.xgimiapiservice.XgimiApiService");
        bindService(new Intent().setComponent(componentName), serviceConnection, Context.BIND_AUTO_CREATE);

    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        //绑定上服务的时候
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtil.e(TAG, "连接AIDL成功");

            //设定为极米设备
            isxgimi = true;

            //得到远程服务
            apiManager = XgimiApiManager.Stub.asInterface(iBinder);

            if (TurnOnS) {
                return;
            }
            try {
//                ID = "DG5CH33C1TAP";
                //设备SN
                SN = apiManager.get("getMachineId", null, null);
                SaveUtils.setString(Save_Key.SerialNum, SN);
                MiPushClient.setUserAccount(context, SN, null);
                //风速
                WindSpeed = apiManager.get("getWindSpeed", null, null);
                SaveUtils.setString(Save_Key.WindSpeed, WindSpeed);
                //温度
                Temp = apiManager.get("getTemp", null, null);
                SaveUtils.setString(Save_Key.Temp, Temp);

                //禁止调焦
                apiManager.set("setFocusOnOff", "true", null, null, null);

                LogUtil.e(TAG, " 序列号 ：" + SN);
                LogUtil.e(TAG, "全局缩放：" + apiManager.get("getZoomValue", null, null));
                LogUtil.e(TAG, "横向缩放：" + apiManager.get("getHorizentalValue", null, null));
                LogUtil.e(TAG, "纵向缩放：" + apiManager.get("getVerticalValue", null, null));
                LogUtil.e(TAG, "标识数据：" + apiManager.get("getMachineSignal", null, null));
                LogUtil.e(TAG, "设备名称：" + apiManager.get("getDeviceName", null, null));
                LogUtil.e(TAG, "亮度模式：" + apiManager.get("getLedMode", null, null));
                LogUtil.e(TAG, "风   速：" + WindSpeed);
                LogUtil.e(TAG, "投影模式：" + apiManager.get("getProjectionMode", null, null));
                LogUtil.e(TAG, "温   度：" + Temp);
                LogUtil.e(TAG, " 开机源 ：" + apiManager.get("getBootSource", null, null));
                LogUtil.e(TAG, "上电开机：" + apiManager.get("getPowerOnStartValue", null, null));
                LogUtil.e(TAG, "设备型号：" + apiManager.get("getDeviceModel", null, null));
//                LogUtil.e(TAG, "设置调焦距：" + apiManager.set("setFocusOnOff", "false", null, null, null));
                LogUtil.e(TAG, "固件版本：" + Build.VERSION.INCREMENTAL);
                modelNum = apiManager.get("getDeviceModel", null, null);
                SaveUtils.setString(Save_Key.modelNum, modelNum);

                //开机方式
                turnType = Boolean.valueOf(apiManager.get("getPowerOnStartValue", null, null)) ? "1" : "2";

                SaveUtils.setString(Save_Key.turnType, turnType);

                LogUtil.e(TAG, "梯形角度：" + apiManager.get("getKeyStoneData", null, null));

                String jsonStr = apiManager.get("getKeyStoneData", null, null);

                try {
                    point = new Gson().fromJson(jsonStr, Point.class);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage());
                    point = null;
                }

                if (!TurnOnS) {
                    new TurnOn_servlet(context).execute();
                }

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
