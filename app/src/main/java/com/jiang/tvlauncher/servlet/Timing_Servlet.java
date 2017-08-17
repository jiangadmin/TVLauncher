package com.jiang.tvlauncher.servlet;

import android.content.Context;
import android.os.AsyncTask;
import android.os.RemoteException;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.entity.BaseEntity;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.LogUtil;

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
public class Timing_Servlet extends AsyncTask<String, Integer, BaseEntity> {

    private static final String TAG = "Timing_Servlet";

    @Override
    protected BaseEntity doInBackground(String... infos) {

        Map map = new HashMap();

        map.put("devId", MyAppliaction.ID);
        map.put("netSpeed", "1");

            map.put("cpuTemp", "10");

        map.put("fanSpeed", "1");
        String res = HttpUtil.doPost(Const.URL + "dev/devRunStateController/monitorRunState.do", map);
        BaseEntity entity;
        if (res!=null){
            try {
                entity = new Gson().fromJson(res,BaseEntity.class);
            }catch (Exception e){
                entity = new BaseEntity();
                entity.setErrorcode(-2);
                entity.setErrormsg("数据解析失败");
            }

        }else {
            entity = new BaseEntity();
            entity.setErrorcode(-1);
            entity.setErrormsg("连接服务器失败");
        }

        return entity;
    }


    @Override
    protected void onPostExecute(BaseEntity entity) {
        super.onPostExecute(entity);
    }

}
