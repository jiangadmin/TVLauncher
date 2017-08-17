package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.FindLanunch;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.SaveUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiang
 * on 2017/8/9.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 获取广告投放列表
 * update：
 */

public class FindLanunch_Servlet extends AsyncTask<String, Integer, FindLanunch> {
    private static final String TAG = "FindLanunch_Servlet";

    @Override
    protected FindLanunch doInBackground(String... strings) {
        Map map = new HashMap();
        map.put("devId",SaveUtils.getString(Save_Key.ID));
        String res = HttpUtil.doPost(Const.URL + "cms/launchController/findLaunchList.do", map);
        FindLanunch lanunch;
        if (res != null) {
            try {
                lanunch = new Gson().fromJson(res, FindLanunch.class);
            } catch (Exception e) {
                lanunch = new FindLanunch();
                lanunch.setErrorcode(-1);
                lanunch.setErrormsg("连接服务器失败");
            }
        } else {
            lanunch = new FindLanunch();
            lanunch.setErrorcode(-1);
            lanunch.setErrormsg("连接服务器失败");
        }
        return lanunch;
    }

    @Override
    protected void onPostExecute(FindLanunch findLanunch) {
        super.onPostExecute(findLanunch);
    }
}
