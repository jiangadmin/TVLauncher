package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.entity.TurnOnEntity;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;

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
        if (entity.getErrorcode()==1000) {
            Const.ID = entity.getResult().getDevInfo().getId();
            SaveUtils.setString(Save_Key.ID, String.valueOf(entity.getResult().getDevInfo().getId()));

            new FindLanunch_Servlet().execute();
            new FindChannelList_Servlet().execute();

        } else {
            LogUtil.e(TAG, "失败了" + entity.getErrormsg());
        }


//        new Register_Servlet(MyAppliaction.context).execute();
    }
}
