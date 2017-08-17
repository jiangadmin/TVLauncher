package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.FindChannelList;
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
 * Purpose:TODO 获取栏目列表
 * update：
 */

public class FindChannelList_Servlet extends AsyncTask<String, Integer, FindChannelList> {
    private static final String TAG = "FindChannelList_Servlet";

    @Override
    protected FindChannelList doInBackground(String... strings) {
        Map map = new HashMap();
        map.put("devId", SaveUtils.getString(Save_Key.ID));
        String res = HttpUtil.doPost(Const.URL + "cms/channelController/findChannelList.do", map);
        FindChannelList channelList;
        if (res != null) {
            try {
                channelList = new Gson().fromJson(res, FindChannelList.class);
            } catch (Exception e) {
                channelList = new FindChannelList();
                channelList.setErrorcode(-1);
                channelList.setErrormsg("连接服务器失败");
            }
        } else {
            channelList = new FindChannelList();
            channelList.setErrorcode(-1);
            channelList.setErrormsg("连接服务器失败");
        }
        return channelList;
    }

    @Override
    protected void onPostExecute(FindChannelList channelList) {
        super.onPostExecute(channelList);
    }
}
