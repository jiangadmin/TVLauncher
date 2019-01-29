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

import com.TvTicketTool.TvTicketTool;
import com.google.gson.Gson;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.Point;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.servlet.TurnOn_servlet;
import com.jiang.tvlauncher.servlet.VIPCallBack_Servlet;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.Tools;
import com.ktcp.video.ktsdk.TvTencentSdk;
import com.ktcp.video.thirdagent.JsonUtils;
import com.ktcp.video.thirdagent.KtcpContants;
import com.ktcp.video.thirdagent.KtcpPaySDKCallback;
import com.ktcp.video.thirdagent.KtcpPaySdkProxy;
import com.tencent.bugly.crashreport.CrashReport;
import com.xgimi.xgimiapiservice.XgimiApiManager;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by  jiang
 * on 2017/7/3.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO
 * update：
 */

public class MyApp extends Application implements KtcpPaySDKCallback {
    private static final String TAG = "MyApp";
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

        //添加腾讯监听
        KtcpPaySdkProxy.getInstance().setPaySDKCallback(this);


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

    private int status = -1;//接口状态码
    private String msg;//接口提示信息

    /**
     * @param channel 三方厂商对应的渠道号
     * @param extra   包含guid,QUA，TVPlatform等字段的json字符串
     */
    @Override
    public void doLogin(String channel, String extra) {

        final HashMap<String, Object> loginData = new HashMap<>();
        // FIXME:  获取帐号   需要腾讯处理的错误码和提示请沟通好通知处理
        // status 成功返回 0 失败返回对应错误码 厂商业务错误 fixme  腾旅 902xxx 例如902001登录失败
        // msg  错误提示
        // data json数据

        //vuid登录示例
        loginData.put("loginType", "vu");//登录类型 vu ,qq,wx,ph
        loginData.put("vuid", Const.ktcp_vuid);
        loginData.put("vtoken", Const.ktcp_vtoken);
        loginData.put("accessToken", Const.ktcp_accessToken);


        //大票换小票接口
        TvTicketTool.getVirtualTVSKey(this, false, Long.parseLong(Const.ktcp_vuid), Const.ktcp_vtoken, Const.ktcp_accessToken, new TvTencentSdk.OnTVSKeyListener() {
            @Override
            public void OnTVSKeySuccess(String vusession, int expiredTime) {
                LogUtil.e(TAG, "vusession=" + vusession + ",expiredTime=" + expiredTime);
                status = 0;
                msg = "login success";
                loginData.put("vusession", vusession);
                //通过onLoginResponse 将数据回传给腾讯
                KtcpPaySdkProxy.getInstance().onLoginResponse(status, msg, JsonUtils.addJsonValue(loginData));
            }

            @Override
            public void OnTVSKeyFaile(int failedCode, String failedMsg) {
                LogUtil.e(TAG, "failedCode=" + failedCode + ",msg=" + failedMsg);
                status = failedCode;
                msg = failedMsg;
                KtcpPaySdkProxy.getInstance().onLoginResponse(status, msg, JsonUtils.addJsonValue(loginData));
            }
        });
    }

    /**
     * 订单处理  需要腾讯处理的错误码和提示请沟通好通知处理
     *
     * @param vuserId
     * @param productId
     * @param extra
     */
    @Override
    public void doOrder(String vuserId, String productId, String extra) {

        // status 成功返回0 失败返回对应错误码 厂商业务错误 fixme  腾旅 902xxx 例如902101订购失败
        // msg  错误提示
        // data json数据 { "vuid":"","extra":{"orderId":""}}

        //订单返回结果示例
        HashMap<String, Object> params = new HashMap<>();
        int status = 0;//0 订单处理成功 非0失败
        String msg = "make order success";
        params.put("vuid", vuserId);

        JSONObject orderJson;
        try {
            orderJson = new JSONObject();
            orderJson.put("orderId", "orderId12345678");
        } catch (JSONException e) {
            e.printStackTrace();
            orderJson = new JSONObject();
        }
        params.put("extra", orderJson);

        KtcpPaySdkProxy.getInstance().onOrderResponse(status, msg, JsonUtils.addJsonValue(params));

    }

    /**
     * 接收来自腾讯的通知事件
     *
     * @param eventId 1 接收应用启动事件 由三方APP决定登录时机发起登录 2 帐号登录回调  3 帐号退出回调 4 APP退出回调
     * @param params
     */
    @Override
    public void onEvent(int eventId, String params) {
        LogUtil.e(TAG, "eventId:" + eventId + "params:" + params);
        switch (eventId) {
            //接收应用启动事件 由三方APP决定登录时机发起登录
            case 1:
                KtcpPaySdkProxy.getInstance().onEventResponse(KtcpContants.EVENT_ACCOUNT_LOGIN, "");
                break;
            //帐号登录回调
            case 2:
                //示例  {{"extra":"{\"isVip\":false,\"vuid\":278113277,\"msg\":\"login success\",\"code\":0,\"vuSession\":\"97027a6822cce5220250ef76cd58\"}","eventId":2,"type":3}
                break;
            case 3://退出登录回调
                break;
            case 4://APP退出
                break;
            default:
                break;
        }

        try {
            JSONObject extraObj = JsonUtils.getJsonObj(params);
            int code = extraObj.optInt("code");
            String message = extraObj.optString("msg");

            VIPCallBack_Servlet.TencentVip vip = new VIPCallBack_Servlet.TencentVip();
            vip.setCode(String.valueOf(code));
            vip.setMsg(message);
            vip.setEventId(String.valueOf(eventId));  // 2 账户登录回调 3 退出登录  4 APP退出
            new VIPCallBack_Servlet().execute(vip);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }

    }

}
