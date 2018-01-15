package com.jiang.tvlauncher.servlet;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.activity.Home_Activity;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.Point;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.entity.TurnOnEntity;
import com.jiang.tvlauncher.server.TimingService;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.Tools;
import com.jiang.tvlauncher.utils.WifiApUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  jiang
 * on 2017/6/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 开机发送
 * update：
 */

public class TurnOn_servlet extends AsyncTask<String, Integer, TurnOnEntity> {
    private static final String TAG = "TurnOn_servlet";
    Context context;

    TimeCount timeCount;

    public TurnOn_servlet(Context context) {
        this.context = context;
        timeCount = new TimeCount(3000, 1000);
    }

    @Override
    protected TurnOnEntity doInBackground(String... strings) {
        Map map = new HashMap();
        TurnOnEntity entity;

        if (TextUtils.isEmpty(MyAppliaction.ID)) {
            if (!TextUtils.isEmpty(SaveUtils.getString(Save_Key.ID))) {
                MyAppliaction.ID = SaveUtils.getString(Save_Key.ID);
                MyAppliaction.turnType = SaveUtils.getString(Save_Key.turnType);
                MyAppliaction.modelNum = SaveUtils.getString(Save_Key.modelNum);
            } else {
                new TurnOn_servlet(context).execute();
                entity = new TurnOnEntity();
                entity.setErrorcode(-3);
                entity.setErrormsg("数据缺失 再来一次");
                return entity;
            }
        }

        map.put("serialNum", MyAppliaction.ID);
        map.put("turnType", MyAppliaction.turnType);
        map.put("modelNum", MyAppliaction.modelNum);

        map.put("systemVersion", Build.VERSION.INCREMENTAL);
        map.put("androidVersion", Build.VERSION.RELEASE);

        String res = HttpUtil.doPost(Const.URL + "dev/devTurnOffController/turnOn.do", map);

        if (res != null) {
            try {
                entity = new Gson().fromJson(res, TurnOnEntity.class);
            } catch (Exception e) {
                entity = new TurnOnEntity();
                entity.setErrorcode(-2);
                entity.setErrormsg("数据解析失败");
                LogUtil.e(TAG, e.getMessage());
            }
        } else {
            entity = new TurnOnEntity();
            entity.setErrorcode(-1);
            entity.setErrormsg("连接服务器失败");
        }

        return entity;
    }

    @Override
    protected void onPostExecute(TurnOnEntity entity) {
        super.onPostExecute(entity);
        Const.Nets = false;
        Loading.dismiss();

        LogUtil.e(TAG, "=======================================================================================");
        if (entity != null && entity.getErrormsg() != null)
            LogUtil.e(TAG, entity.getErrormsg());
//        Toast.makeText(context, "开机请求返回："+entity.getErrormsg(), Toast.LENGTH_SHORT).show();
        LogUtil.e(TAG, "=======================================================================================");

        if (entity.getErrorcode() == 1000) {
            MyAppliaction.TurnOnS = true;

            //归零
            num = 0;

            //先关闭热点
            WifiApUtils.getInstance(context).closeWifiAp();

            Const.ID = entity.getResult().getDevInfo().getId();

            //存储ID
            SaveUtils.setString(Save_Key.ID, String.valueOf(entity.getResult().getDevInfo().getId()));

            //存储密码
            if (entity.getResult().getShadowcnf() != null && entity.getResult().getShadowcnf().getShadowPwd() != null) {
                SaveUtils.setString(Save_Key.Password, entity.getResult().getShadowcnf().getShadowPwd());
            } else {
                SaveUtils.setString(Save_Key.Password, "");
            }
            //更改开机动画
            if (entity.getResult().getLaunch() != null)
                if (!TextUtils.isEmpty(entity.getResult().getLaunch().getMediaUrl())) {
                    LogUtil.e(TAG, entity.getResult().getLaunch().getMediaUrl());
                    SaveUtils.setString(Save_Key.BootAn, entity.getResult().getLaunch().getMediaUrl());
                }

            //方案类型（1=开机，2=屏保，3=互动）
            if (entity.getResult().getLaunch() != null)
                if (entity.getResult().getLaunch().getLaunchType() == 1) {
                    //非空判断
                    if (!TextUtils.isEmpty(entity.getResult().getLaunch().getMediaUrl())) {
                        //图片
                        if (entity.getResult().getLaunch().getMediaType() == 1) {
                            SaveUtils.setBoolean(Save_Key.NewImage, true);
                            SaveUtils.setBoolean(Save_Key.NewVideo, false);
                            SaveUtils.setString(Save_Key.NewImageUrl, entity.getResult().getLaunch().getMediaUrl());
                        }
                        //视频
                        if (entity.getResult().getLaunch().getMediaType() == 2) {
                            SaveUtils.setBoolean(Save_Key.NewVideo, true);
                            SaveUtils.setBoolean(Save_Key.NewImage, false);
                            SaveUtils.setString(Save_Key.NewVideoUrl, entity.getResult().getLaunch().getMediaUrl());
                        }
                    }
                }

            String s = entity.getResult().getDevInfo().getZoomVal();
            LogUtil.e(TAG, "梯形数据:" + s);

            try {
                //初始化设备名称
                MyAppliaction.apiManager.set("setDeviceName", entity.getResult().getDevInfo().getModelNum(), null, null, null);


                //初始化上电开机
                if (entity.getResult().getShadowcnf() != null) {

                    //投影方式开关
                    if (entity.getResult().getShadowcnf().getProjectMode()==1) {
                        MyAppliaction.apiManager.set("setProjectionMode", String.valueOf(entity.getResult().getShadowcnf().getProjectMode()), null, null, null);
                    }

                    //上电开机开关
                    if (entity.getResult().getShadowcnf().getPowerTurn() == 1) {
                        MyAppliaction.apiManager.set("setPowerOnStart", "true", null, null, null);
                    } else {
                        MyAppliaction.apiManager.set("setPowerOnStart", "false", null, null, null);
                    }

                    //梯形校正开关
                    if (entity.getResult().getShadowcnf().getZoomFlag()==1) {
                        //初始化梯形数据
                        Point point = new Gson().fromJson(s, Point.class);
                        for (int i = 0; i < point.getPoint().size(); i++) {
                            MyAppliaction.apiManager.set("setKeyStoneByPoint", point.getPoint().get(i).getIdx(), point.getPoint().get(i).getCurrent_x(), point.getPoint().get(i).getCurrent_y(), null);
                        }
                    }
                }
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
            }

            //存储间隔时间
            if (entity.getResult().getShadowcnf() != null)
                SaveUtils.setInt(Save_Key.Timming, entity.getResult().getShadowcnf().getMonitRate());

            //启动定时服务
            context.startService(new Intent(context, TimingService.class));

            //获取开屏
            new FindLanunch_Servlet().execute();

            if (MyAppliaction.activity != null && MyAppliaction.activity.getClass() == Home_Activity.class) {
                ((Home_Activity) MyAppliaction.activity).update();
            }

            //判断是否是有线连接
            if (Tools.isLineConnected() && entity.getResult().getShadowcnf() != null
                    && entity.getResult().getShadowcnf().getWifi() != null
                    && entity.getResult().getShadowcnf().getWifiPassword() != null
                    && entity.getResult().getShadowcnf().getHotPoint() == 1) {

                String SSID = entity.getResult().getShadowcnf().getWifi();
                String APPWD = entity.getResult().getShadowcnf().getWifiPassword();

                SaveUtils.setString(Save_Key.WiFiName, SSID);
                SaveUtils.setString(Save_Key.WiFiPwd, APPWD);

                LogUtil.e(TAG, "SSID:" + SSID + "  PassWord:" + APPWD);

                //打开并设置热点信息.注意热点密码8-32位，只限制了英文密码位数。
                if (WifiApUtils.getInstance(context).openWifiAp(SSID, APPWD))
                    Toast.makeText(context, "热点开启成功！", Toast.LENGTH_SHORT).show();

                //开启wifiAp
//                new Wifi_APManager(context).createAp(SSID, APPWD);
            } else
                WifiApUtils.getInstance(context).closeWifiAp();

        } else if (entity.getErrorcode() == -2) {
            LogUtil.e(TAG, entity.getErrormsg());

        } else {
            timeCount.start();
            LogUtil.e(TAG, "失败了" + entity.getErrormsg());
        }
    }

    public static int num = 0;

    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        //倒计时完成
        @Override
        public void onFinish() {
            num++;
            //再次启动
            new TurnOn_servlet(context).execute();

            //即没连接wifi也没插网线
//            if (!Tools.isWifiConnected() && !Tools.isLineConnected() && !Tools.isNetworkConnected() && num == 1)
//
//                new NetWarningDialog(MyAppliaction.activity).show();
//            else {
//                //如果是有线网络接入
//                if (Tools.isLineConnected() && num == 3 && MyAppliaction.activity != null)
//                    new NetWarningDialog(MyAppliaction.activity).show();
//
//                //如果是WIFI接入
//                if (Tools.isWifiConnected() && num == 10 && MyAppliaction.activity != null)
//                    new NetWarningDialog(MyAppliaction.activity).show();
//            }
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

        }
    }
}
