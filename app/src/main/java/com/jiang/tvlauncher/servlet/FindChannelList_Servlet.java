package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiang.tvlauncher.activity.Home_Activity;
import com.jiang.tvlauncher.dialog.Loading;
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
    Home_Activity activity;

    public static int num = 1;
    String res;

    public FindChannelList_Servlet(Home_Activity activity) {
        this.activity = activity;
    }

    @Override
    protected FindChannelList doInBackground(String... strings) {
        Map map = new HashMap();
        FindChannelList channelList;
        if (TextUtils.isEmpty(SaveUtils.getString(Save_Key.ID))) {
            channelList = new FindChannelList();
            channelList.setErrorcode(-3);
            channelList.setErrormsg("数据缺失");
            return channelList;
        }
        map.put("devId", SaveUtils.getString(Save_Key.ID));
        res = HttpUtil.doPost(Const.URL + "cms/channelController/findChannelList.do", map);

        if (res != null) {
            try {
                channelList = new Gson().fromJson(res, FindChannelList.class);
            } catch (Exception e) {
                channelList = new FindChannelList();
                channelList.setErrorcode(-2);
                channelList.setErrormsg("数据解析失败");
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
        Loading.dismiss();
        if (channelList.getErrorcode() == 1000) {
            SaveUtils.setString(Save_Key.Channe, res);
            activity.updateshow(channelList);
        }
        if ((channelList.getErrorcode() == -3) || (channelList.getErrorcode() == -1))
            if (num > 3) {
                activity.updateshow(new Gson().fromJson(SaveUtils.getString(Save_Key.Channe), FindChannelList.class));
            } else {
                num++;
                new FindChannelList_Servlet(activity).execute();
            }
    }
}
