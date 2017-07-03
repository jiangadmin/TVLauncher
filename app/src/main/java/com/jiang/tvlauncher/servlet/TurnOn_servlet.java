package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;

import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.Tools;

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

public class TurnOn_servlet extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... strings) {
        Map map = new HashMap();
        map.put("text", "开机发送请求");
        map.put("设备号", "开机发送请求");
        map.put("版本号", "开机发送请求");

        do {
            HttpUtil.doPost(Const.URL + "TurnOn", map);


        } while (!Tools.ping());

        return null;
    }

}
