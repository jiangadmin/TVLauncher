package com.jiang.tvlauncher.servlet;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.dialog.WarnDialog;
import com.jiang.tvlauncher.entity.BaseEntity;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.MonitorResEntity;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.utils.FileUtils;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.ShellUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  jiang
 * on 2017/6/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 定时发送
 * update：
 */
public class Timing_Servlet extends AsyncTask<String, Integer, MonitorResEntity> {

    private static final String TAG = "Timing_Servlet";

    private static boolean sleep = false;

    @Override
    protected MonitorResEntity doInBackground(String... infos) {
        Map map = new HashMap();
        map.put("devId", SaveUtils.getString(Save_Key.ID));
        map.put("netSpeed", "1");
        map.put("storage", FileUtils.getRomSize());
        map.put("memoryInfo", FileUtils.getAvailMemory());
        map.put("avaSpace", FileUtils.getFreeDiskSpaceS());
        try {
            map.put("cpuTemp", MyAppliaction.apiManager.get("getTemp", null, null));
            map.put("fanSpeed", MyAppliaction.apiManager.get("getWindSpeed", null, null));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("cpuTemp", "0");
            map.put("fanSpeed", "0");
        }
        String res = HttpUtil.doPost(Const.URL + "dev/devRunStateController/monitorRunState.do", map);
        MonitorResEntity entity;
        if (res != null) {
            try {
                entity = new Gson().fromJson(res, MonitorResEntity.class);
            } catch (Exception e) {
                entity = new MonitorResEntity();
                entity.setErrorcode(-2);
                entity.setErrormsg("数据解析失败");
            }

        } else {
            entity = new MonitorResEntity();
            entity.setErrorcode(-1);
            entity.setErrormsg("连接服务器失败");
        }
        return entity;
    }

    @Override
    protected void onPostExecute(MonitorResEntity entity) {
        super.onPostExecute(entity);
        switch (entity.getErrorcode()) {
            case 1000:
                if (entity.getResult().getBussFlag() == 0) {

                    Const.BussFlag = 0;

                    Intent backHome = new Intent(Intent.ACTION_MAIN);

                    backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    backHome.addCategory(Intent.CATEGORY_HOME);

                    MyAppliaction.context.startActivity(backHome);
//                    try {
//                        ShellUtils.execCommand("input keyevent 3", false);
//                    } catch (Exception ex) {
//                        Log.e(TAG, "onPostExecute: " + ex.getMessage());
//                    }

                } else if (entity.getResult().getBussFlag() == 1) {
                    Const.BussFlag = 1;
                }

                EventBus.getDefault().post(String.valueOf(entity.getResult().getBussFlag()));
                break;
        }

    }
}
