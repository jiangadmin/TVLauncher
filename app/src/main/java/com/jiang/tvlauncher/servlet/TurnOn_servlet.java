package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;
import android.widget.Toast;

import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.utils.HttpUtil;

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
    private static final String TAG = "TurnOn_servlet";

    @Override
    protected String doInBackground(String... strings) {
        Map map = new HashMap();
//        map.put("text", "开机发送请求");
        map.put("serialNum", "1234567");
        map.put("turnType", "1");

        HttpUtil.doPost(Const.URL + "dev/devTurnOffController/turnOn.do", map);

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Const.Nets = false;

        Toast.makeText(MyAppliaction.context, "发出开机请求", Toast.LENGTH_SHORT).show();

//        new Register_Servlet(MyAppliaction.context).execute();
    }
}
