package com.jiang.tvlauncher;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiang.tvlauncher.entity.Point;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.servlet.TurnOn_servlet;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.Tools;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
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

    public static boolean IsLineNet = true;//是否是有线网络
    public static String modelNum = "Z5";
    public static String ID = "FFFFFF";
    public static String Temp = "FFFFFF";
    public static String WindSpeed = "FFFFFF";
    public static String turnType = "2";//开机类型 1 通电开机 2 手动开机
    Point point;

    public static boolean TurnOnS = false;

    @Override
    public void onCreate() {
        super.onCreate();
//        startService(new Intent(this, TimingService.class));
        context = this;

        //初始化ImageLoader
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));

        LogUtil.e(TAG,"有线连接："+Tools.isLineConnected());

        SaveUtils.setBoolean(Save_Key.FristTurnOn, true);
        LogUtil.e(TAG, "准备连接AIDL");
        ComponentName componentName = new ComponentName("com.xgimi.xgimiapiservice", "com.xgimi.xgimiapiservice.XgimiApiService");
        bindService(new Intent().setComponent(componentName), serviceConnection, Context.BIND_AUTO_CREATE);

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
                //允许开机自启动
//                apiManager.set("setAutoStartApk","Feekr","true",null,null);

                //禁止调焦
                apiManager.set("setFocusOnOff", "false", null, null, null);

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
                LogUtil.e(TAG, "设备型号：" + apiManager.get("getDeviceModel", null, null));
//                LogUtil.e(TAG, "调焦距：" + apiManager.get("getFocusOnOffValue",null,null));
                LogUtil.e(TAG, "设置调焦距：" + apiManager.set("setFocusOnOff", "false", null, null, null));
//                LogUtil.e(TAG, "调焦距：" + apiManager.get("getFocusOnOffValue",null,null));
                LogUtil.e(TAG, "固件版本：" + Build.VERSION.INCREMENTAL);
                modelNum = apiManager.get("getDeviceModel", null, null);
                if (Boolean.valueOf(apiManager.get("getPowerOnStartValue", null, null)))
                    turnType = "1";
                else
                    turnType = "2";
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

                new TurnOn_servlet(context).execute();

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
