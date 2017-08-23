package com.jiang.tvlauncher.servlet;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.Point;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.entity.TurnOnEntity;
import com.jiang.tvlauncher.server.TimingService;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.ImageUtils;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.Wifi_APManager;
import com.nostra13.universalimageloader.core.ImageLoader;

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

    public TurnOn_servlet(Context context) {
        this.context = context;
    }

    @Override
    protected TurnOnEntity doInBackground(String... strings) {
        Map map = new HashMap();
//        map.put("text", "开机发送请求");
        map.put("serialNum", MyAppliaction.ID);
        map.put("turnType", "1");

        String res = HttpUtil.doPost(Const.URL + "dev/devTurnOffController/turnOn.do", map);

        TurnOnEntity entity;
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
        if (entity.getErrorcode() == 1000) {
            Const.ID = entity.getResult().getDevInfo().getId();
            //存储ID
            SaveUtils.setString(Save_Key.ID, String.valueOf(entity.getResult().getDevInfo().getId()));

            //存储密码
            SaveUtils.setString(Save_Key.Password, entity.getResult().getShadowcnf().getShadowPwd());

            //设置热点名  热点密码
            new Wifi_APManager(context).startWifiAp(entity.getResult().getShadowcnf().getWifi(), entity.getResult().getShadowcnf().getWifiPassword(), true);

            for (int i = 0; i < entity.getResult().getLaunch().size(); i++) {
                //方案类型（1=开机，2=屏保，3=互动）
                if (entity.getResult().getLaunch().get(i).getLaunchType() == 1) {
                    //非空判断
                    if (!TextUtils.isEmpty(entity.getResult().getLaunch().get(i).getMediaUrl())) {

                        //图片
                        if (entity.getResult().getLaunch().get(i).getMediaType() == 1) {
                            SaveUtils.setBoolean(Save_Key.NewImage, true);
                            SaveUtils.setBoolean(Save_Key.NewVideo, false);
                            ImageUtils.setimgage(ImageLoader.getInstance().loadImageSync(entity.getResult().getLaunch().get(i).getMediaUrl()), "welcomeImage");

                        }

                        //视频
                        if (entity.getResult().getLaunch().get(i).getMediaType() == 2) {
                            SaveUtils.setBoolean(Save_Key.NewVideo, true);
                            SaveUtils.setBoolean(Save_Key.NewImage, false);
                            SaveUtils.setString(Save_Key.NewVideoUrl, entity.getResult().getLaunch().get(i).getMediaUrl());
                        }
                    }
                }
            }

            //初始化梯形数据
            try {
                Point point = new Gson().fromJson(entity.getResult().getDevInfo().getZoomVal(), Point.class);
                for (int i = 0; i < point.getPoint().size(); i++) {
                    MyAppliaction.apiManager.set("setKeyStoneByPoint", point.getPoint().get(i).getIdx(), point.getPoint().get(i).getCurrent_x(), point.getPoint().get(i).getCurrent_y(), null);
                }
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
            }

            //存储间隔时间
            SaveUtils.setInt(Save_Key.Timming, entity.getResult().getShadowcnf().getMonitRate());

            //启动定时服务
            context.startService(new Intent(context, TimingService.class));
            //获取开屏
            new FindLanunch_Servlet().execute();
            //获取更新
            new Update_Servlet().execute();



        } else {
            //再次启动
            new TurnOn_servlet(context).execute();

            LogUtil.e(TAG, "失败了" + entity.getErrormsg());
        }

    }
}
